# Springboot HATEOAS

Demonstração do uso de HATEOAS para um projeto Springboot REST

## Endpoints API

### Home Message

* Indice

	http://localhost:8080/api

* All-Messages

	http://localhost:8080/api/messages

* All-Messages montado via Assembler

	http://localhost:8080/api/messages/assembler

* All-Messages montado via Paginação (Spring Data), indica Next e Previous

	http://localhost:8080/api/messages/pag?page=1&size=3

### Products

* CRUD Products, CREATE, GET-ALL

	http://localhost:8080/api/products

* GET-ID, UPDATE-ID, DELETE-ID

	http://localhost:8080/api/products/{id}

* Postman Body Raw JSON

	{
	  "id": 1,
	  "name": "Produto A",
	  "price": 100.0
	}

## Analises e Opiniões

Analise adicional sobre [Roy Fielding](docs/roy-fielding.md)

Analise adicional sobre [RPC vs. HATEOAS](docs/rpc-hateoas.md)

Analise adicional sobre [Segurança ao compartilhar Links](docs/sec-share-links.md)