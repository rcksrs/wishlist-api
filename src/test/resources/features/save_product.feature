#language: pt
Funcionalidade: Salvar produto na wishlist do usuário

  Cenário: Salvar um produto para um novo usuário
    Dado que o usuário com id "123" deseja adicionar um produto à sua wishlist
    E o produto tem os seguintes detalhes:
      | sku  | title        | price |
      | 100  | Novo Produto | 10.00 |
    Quando o usuário envia a requisição para salvar o produto na wishlist
    Então a resposta deve ter o status 201
    E a resposta deve conter o produto com nome "Novo Produto" e SKU "100"

  Cenário: Salvar um produto para um usuário existente
    Dado que o usuário com id "123" deseja adicionar um produto à sua wishlist
    E o produto tem os seguintes detalhes:
      | sku  | title          | price |
      | 200  | Novo Produto 2 | 10.00 |
    Quando o usuário envia a requisição para salvar o produto na wishlist
    Então a resposta deve ter o status 201
    E a resposta deve conter o produto com nome "Novo Produto 2" e SKU "200"

  Cenário: Salvar um produto com mesmo SKU para um usuário existente
    Dado que o usuário com id "123" deseja adicionar um produto à sua wishlist
    E o produto tem os seguintes detalhes:
      | sku  | title          | price |
      | 200  | Novo Produto 3 | 10.00 |
    Quando o usuário envia a requisição para salvar o produto na wishlist
    Então a resposta deve ter o status 201
    E a resposta deve conter o produto com nome "Novo Produto 2" e SKU "200"
    E a resposta não deve conter o produto com nome "Novo Produto 3"

  Cenário: Salvar um produto sem título
    Dado que o usuário com id "123" deseja adicionar um produto à sua wishlist
    E o produto tem os seguintes detalhes:
      | sku  | price |
      | 200  | 10.00 |
    Quando o usuário envia a requisição para salvar o produto na wishlist
    Então a resposta deve ter o status 400
    E a validação do campo "title" deve conter o erro "não deve estar em branco"

  Cenário: Salvar um produto sem SKU
    Dado que o usuário com id "123" deseja adicionar um produto à sua wishlist
    E o produto tem os seguintes detalhes:
      | title           | price |
      | Novo Produto 4  | 10.00 |
    Quando o usuário envia a requisição para salvar o produto na wishlist
    Então a resposta deve ter o status 400
    E a validação do campo "sku" deve conter o erro "não deve estar em branco"