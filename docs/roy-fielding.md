# Roy Fielding

Tese de Doutorado de Roy Fielding

[Architectural styles and the design of network-based software architectures](https://scholar.google.com/citations?view_op=view_citation&hl=en&user=y-2KcWwAAAAJ&citation_for_view=y-2KcWwAAAAJ:u5HHmVD_uO8C)

Gostaria de abrir um espaço pra comentar sobre uma frase polemica do Doutorado de Roy Fielding. Essa frase costuma aparecer parafraseada como:

	“APIs que não usam HATEOAS não são REST, são RPC sobre HTTP.”

E, dentro do contexto acadêmico da tese, ele não está errado.

## O que o Fielding quis dizer

Na tese, REST **não é sinônimo de “API JSON”**.
REST é um **estilo arquitetural**, com *constraints* bem específicas:

1. Client–Server
2. Stateless
3. Cacheable
4. Uniform Interface
5. Layered System
6. **Code on Demand (opcional)**

O **HATEOAS** é parte **fundamental da Uniform Interface**.

A ideia central:

> O cliente **não deve conhecer a estrutura da API previamente**
> Ele navega pelos recursos **a partir dos links recebidos**

Sem isso, o cliente está:

* codificando URLs
* conhecendo fluxos de navegação
* acoplado à API

Isso é **RPC (Remote Procedure Call)**, só que usando HTTP como transporte.

Ele está se referindo a um modelo de comunicação mais direto, onde a interação com a API é mais como uma chamada de método remoto (Remote Procedure Call) do que uma navegação entre recursos com links e hipermídia

## Onde o mercado “relaxou” o conceito

Na prática, a indústria fez isso:

* pegou **JSON**
* usou **HTTP verbs**
* ignorou **hipermídia**
* chamou tudo de “REST”

Resultado:

* APIs funcionam? ✔️ Sim
* São REST segundo Fielding? ❌ Não
* São úteis? ✔️✔️✔️ Sim

Fielding **não estava projetando APIs corporativas**, mas um **modelo para a Web em larga escala**.

## A diferença conceitual

### ❌ REST sem HATEOAS (REST-like / RPC)

```http
GET /users/1
```

Resposta:

```json
{
  "id": 1,
  "name": "Ana"
}
```

O cliente *tem que saber*:

* que existe `/users`
* que `/users/{id}` funciona
* qual verbo usar

---

### ✅ REST com HATEOAS (REST “puro”)

```json
{
  "id": 1,
  "name": "Ana",
  "_links": {
    "self": { "href": "/users/1" },
    "orders": { "href": "/users/1/orders" },
    "update": { "href": "/users/1", "method": "PUT" }
  }
}
```

Agora:

* o cliente **descobre** o que pode fazer
* a API pode evoluir sem quebrar clientes
* navegação ≈ Web

Isso é o coração da tese.

## O ponto mais importante (e mais honesto)

👉 **REST não é um selo de qualidade.**
👉 É um **trade-off**.

HATEOAS:

* ✅ desacopla cliente
* ✅ permite evolução
* ❌ aumenta complexidade
* ❌ nem todo cliente usa (mobile, frontend moderno)

Por isso:

* APIs públicas → HATEOAS faz MUITO sentido
* APIs internas → muitas vezes é overkill
* Microservices → quase sempre RPC (e tudo bem)

## Em resumo (estilo Fielding)

* Sem HATEOAS → **não é REST estrito**
* Mas pode ser **uma ótima API**
* O problema é chamar de REST sem entender o custo
