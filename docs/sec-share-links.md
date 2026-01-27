# Segurança ao compartilhar Links

Devemos tomar muito cuidado com a navegação de links no contexto de **segurança**. A **exposição inadvertida de links privados ou sensíveis** é um risco real ao usar HATEOAS, porque ao fornecer links diretamente nas respostas, você pode **acabar incluindo URLs** para **ações não autorizadas** ou recursos que não deveriam ser acessíveis publicamente.

Aqui estão algumas **práticas recomendadas** para garantir que a navegação via HATEOAS **não exponha dados sensíveis**:

## **Cuidados com a Exposição de Links**

### **Filtragem de Links com Base em Permissões**

Antes de retornar links na resposta, **verifique sempre as permissões** do usuário autenticado/autorizado. Isso pode ser feito de várias formas, dependendo da arquitetura de segurança que você usa (como **Spring Security**).

**Exemplo**:
Se você tem um recurso de **usuário** e quer garantir que apenas o próprio usuário veja links para **editar ou excluir** seu perfil, você pode condicionar a resposta dos links dessa forma:

	@GetMapping("/users/{id}")
	public EntityModel<UserDTO> getUser(@PathVariable Long id, Authentication authentication) {
	    UserDTO user = userService.findById(id);
	    
	    // Verifique se o usuário autenticado é o dono do recurso ou tem permissão
	    EntityModel<UserDTO> resource = EntityModel.of(user);
	    
	    if (authentication.getName().equals(user.getUsername())) {
	        // Adiciona links de editar e excluir apenas para o próprio usuário
	        resource.add(linkTo(methodOn(UserController.class).updateUser(id)).withRel("update"));
	        resource.add(linkTo(methodOn(UserController.class).deleteUser(id)).withRel("delete"));
	    }
	    
	    return resource;
	}

Aqui:

* O link para **editar** e **deletar** o usuário só será adicionado **se o usuário autenticado for o próprio dono** do recurso.
* Isso **impede que um usuário comum veja links de edição ou exclusão para outros usuários**.

### **Evitar Links Sensíveis Externamente Visíveis**

Não exiba links para áreas sensíveis da sua API que possam ser acessadas por usuários **não autenticados** ou **não autorizados**. Por exemplo, nunca inclua links como:

* Links para **páginas de login** ou **logout** em respostas de recursos.
* Links para **funções administrativas** ou **funcionalidades internas** (ex: `/admin`, `/admin/deleteUser`).

Esses links **não devem ser acessíveis publicamente**.

**Exemplo perigoso**:

	{
	  "id": 1,
	  "name": "Ana",
	  "_links": {
	    "self": { "href": "/users/1" },
	    "delete": { "href": "/admin/deleteUser/1" }
	  }
	}

Aqui, o link de **exclusão de usuário** é **completamente público**. Isso é um erro, pois um **usuário comum** pode conseguir fazer uma chamada DELETE para a rota `/admin/deleteUser/1`, o que deveria ser acessível apenas por administradores.


## **Estratégias para Respostas Seguras com HATEOAS**

### **Usar um `RepresentationModelAssembler` para Customizar Links**

Você pode usar um `RepresentationModelAssembler` para **personalizar a inclusão de links**, permitindo que você tenha um controle mais granular sobre o que é exposto.

Exemplo de um **Assembler** seguro:

	public class UserModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {
	    @Override
	    public EntityModel<UserDTO> toModel(UserDTO user) {
	        EntityModel<UserDTO> userModel = EntityModel.of(user);
	        
	        // Verifique a autenticação do usuário
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null && auth.getName().equals(user.getUsername())) {
	            // Apenas o próprio usuário pode editar ou excluir seu perfil
	            userModel.add(linkTo(methodOn(UserController.class).updateUser(user.getId())).withRel("update"));
	            userModel.add(linkTo(methodOn(UserController.class).deleteUser(user.getId())).withRel("delete"));
	        }
	        
	        // Adicione links públicos
	        userModel.add(linkTo(methodOn(UserController.class).getUser(user.getId())).withSelfRel());
	        
	        return userModel;
	    }
	}

Nesse exemplo:

* A lógica de **segurança** está centralizada no **Assembler**, o que torna o código **limpo e reutilizável**.
* Você controla diretamente **quais links são gerados** com base nas permissões do usuário autenticado.

### **Autorização Condicional de Links com Base em Roles**

Além de verificar a **propriedade do recurso** (como no caso do próprio usuário), você também pode verificar **permissões de roles**. Por exemplo, um usuário com a role `ADMIN` pode ter links adicionais para recursos administrativos, mas isso deve ser **condicionado à role**.

Exemplo:

	if (auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
	    // Adiciona links administrativos apenas para administradores
	    userModel.add(linkTo(methodOn(AdminController.class).viewAllUsers()).withRel("admin:viewUsers"));
	}

### **Links em Responses de Erro**

Quando você usa HATEOAS com respostas de erro (ex: 404 ou 403), também deve ter cuidado para não expor links que não fazem sentido ou que poderiam **revelar informações sensíveis** sobre a estrutura interna da API.

Por exemplo, se um usuário não tem permissão para acessar um recurso, você **não deveria incluir links** que ele **não pode acessar** na resposta de erro.

	{
	  "message": "Access Denied",
	  "_links": {
	    "self": { "href": "/users/1" }
	  }
	}

Nesse caso, **não inclua** links como `update` ou `delete` na resposta de erro, se o usuário não tem permissão para acessá-los.

## **Boas práticas gerais para HATEOAS seguro**

* **Filtre links**: Exiba links somente para ações que o **usuário pode realmente realizar**.
* **Controle centralizado de links**: Use um **Assembler** ou um **interceptor** para centralizar a lógica de segurança nos links.
* **Não exponha endpoints administrativos**: Links para páginas como `/admin`, `/auth`, ou `/settings` devem ser **condicionados à role do usuário**.
* **Evite expor dados sensíveis**: Cuidado para **não expor dados como senhas ou informações de segurança** nos links ou respostas.

## **Resumo**

HATEOAS é poderoso porque permite **desacoplar o cliente do servidor**, mas você precisa **ter controle total** sobre os links que são expostos, especialmente quando se trata de **segurança**. Sempre que possível, adicione **lógica condicional** para garantir que apenas os links relevantes para o usuário autenticado sejam visíveis. Isso vai impedir que você exponha áreas da API que deveriam ser **protegidas**.