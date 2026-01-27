# RPC vs HATEOAS

## RPC

**RPC (Remote Procedure Call)** é um protocolo em que o cliente faz uma **chamada direta a uma função ou método** no servidor. O cliente não precisa se preocupar com os detalhes do transporte ou com a implementação do servidor; ele apenas faz a chamada de forma abstrata.

### Exemplo básico de RPC:

Imagine uma API que só oferece **ações diretas** (sem links de navegação):

* **Chamada HTTP**:
  `POST /createUser`

* **Corpo da requisição**:

	  {
	    "name": "Ana",
	    "email": "ana@exemplo.com"
	  }

* **Resposta HTTP**:

	  {
	    "id": 1,
	    "name": "Ana",
	    "email": "ana@exemplo.com"
	  }

Aqui, o cliente sabe que **deve chamar `/createUser`** para criar um usuário. Isso é bem **direto**.

### Características do RPC:

* **Comandos** explícitos: o cliente sabe exatamente o que deve fazer.
* **Sem navegação ou links** entre os recursos — é um chamado direto.
* O cliente **acopla** com a implementação do servidor.

Em um sistema **RPC sobre HTTP**, você tem apenas uma **interface de comunicação** entre cliente e servidor, mas sem **abstração de recursos** e **navegação**.


## Como o REST expande isso

Por outro lado, o **REST** (segundo Fielding) quer **desacoplar** o cliente do servidor, permitindo que o cliente descubra o que fazer **navegando por links** (com HATEOAS), em vez de saber explicitamente quais URLs chamar.

### Exemplo REST com HATEOAS:

Imagine uma API que representa um **usuário** com links para o que pode ser feito com ele (como editar, deletar, visualizar pedidos, etc.).

* **Chamada HTTP**:
  `GET /users/1`

* **Resposta HTTP** (em formato HAL ou JSON com links):

	  {
	    "id": 1,
	    "name": "Ana",
	    "_links": {
	      "self": { "href": "/users/1" },
	      "orders": { "href": "/users/1/orders" },
	      "update": { "href": "/users/1", "method": "PUT" },
	      "delete": { "href": "/users/1", "method": "DELETE" }
	    }
	  }

Aqui, o cliente **não sabe de antemão** qual URL chamar para editar ou deletar o usuário. O cliente **descobre** as **ações possíveis** (links) a partir da resposta do servidor. Isso é **HATEOAS**: a **API guia o cliente** no que pode ser feito a seguir, como navegar entre recursos.

### Características do REST (com HATEOAS):

* **Navegação por links**: o cliente descobre o que pode fazer com o recurso.
* **Desacoplamento**: o cliente **não depende do conhecimento sobre a estrutura da API**.
* **Evolução da API**: novos links ou mudanças na estrutura da API **não quebram clientes antigos**.
* O cliente não está **acoplado a uma sequência rígida de chamadas** — pode navegar dinamicamente.


## Expansão do RPC em REST

### 1. **Abstração de recursos**

Em vez de apenas chamar endpoints como **procedimentos remotos**, o cliente interage com **recursos** (entidades de domínio) que estão **modelados** na API. Assim, em vez de ter um endpoint `/createUser`, você pode navegar entre os recursos de usuários e seus pedidos com links.

Exemplo de RPC:
`POST /createUser` → cria um usuário diretamente.

Exemplo de REST:
`GET /users/1` → devolve o recurso "usuário 1", que pode ter links para ações, como "editar" ou "deletar".

### 2. **Descobrimento e flexibilidade**

No RPC, o cliente **precisa saber a ordem exata** das chamadas, o que torna o sistema **rígido**. Em REST, especialmente com HATEOAS, o cliente **não precisa conhecer previamente as URLs**. Ele pode descobrir novos links de navegação à medida que interage com a API, oferecendo **flexibilidade**.

### 3. **Desacoplamento e evolução**

Em um sistema **RPC**, se o servidor mudar algo, como alterar um endpoint ou adicionar novos parâmetros, os **clientes precisam ser atualizados manualmente** para refletir essas mudanças. Com **REST**, os links são dinâmicos e evoluem com a API, o que permite que **novos recursos sejam adicionados sem quebrar os clientes existentes**.

---

## RPC vs REST com HATEOAS

**RPC sobre HTTP**

* Navegação de recursos | Não há navegação. O cliente sabe os endpoints
* Desacoplamento        | O cliente está acoplado à API
* Evolução da API       | Mudanças nos endpoints podem quebrar clientes
* Complexidade          | Mais simples e direto

**REST com HATEOAS**

* Navegação de recursos | Links para outros recursos são incluídos nas respostas
* Desacoplamento        | O cliente é desacoplado e pode navegar conforme necessário
* Evolução da API       | Mudanças podem ser feitas sem quebrar clientes, desde que os links sejam ajustados
* Complexidade          | Mais complexo, mas mais flexível e escalável


### Por que essa expansão de RPC para REST é importante

A grande vantagem do **REST com HATEOAS** (e a razão pela qual Fielding defende isso) é que ele permite **escabilidade e flexibilidade** de longo prazo. Enquanto um sistema **RPC** depende da **construção rígida de clientes** para interagir com cada endpoint, um sistema REST com HATEOAS é mais **auto-documentado** e **flexível**, permitindo que a API cresça sem exigir mudanças constantes nos clientes

Isso é o que **muda** quando você fala em "REST evoluído" — e isso é o que **Fielding** queria destacar. APIs **sem HATEOAS** não são REST de fato, porque falham em fornecer a flexibilidade e a navegação dinâmica que o **estilo arquitetural REST** realmente propõe.