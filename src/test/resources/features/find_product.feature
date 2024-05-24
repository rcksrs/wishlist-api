#language: pt
Funcionalidade: Buscar um produto na wishlist do usuário

  Cenário: Buscar um produto de um usuário existente
    Dado que o usuário com id "789" deseja buscar o produto com SKU "400" na sua wishlist
    Quando o usuário envia a requisição para buscar o produto na wishlist
    Então a resposta da busca deve ter o status 200
    E a resposta da busca deve conter o produto com nome "Novo Produto 4" e SKU "400"

  Cenário: Buscar um produto de um usuário inexistente
    Dado que o usuário com id "200" deseja buscar o produto com SKU "400" na sua wishlist
    Quando o usuário envia a requisição para buscar o produto na wishlist
    Então a resposta da busca deve ter o status 404
    E a resposta da busca deve conter o erro "Wishlist not found"

  Cenário: Buscar um produto inexistente de um usuário existente
    Dado que o usuário com id "789" deseja buscar o produto com SKU "300" na sua wishlist
    Quando o usuário envia a requisição para buscar o produto na wishlist
    Então a resposta da busca deve ter o status 404
    E a resposta da busca deve conter o erro "Product not found"
