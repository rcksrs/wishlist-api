#language: pt
Funcionalidade: Remover um produto da wishlist do usuário

  Cenário: Remover um produto de um usuário existente
    Dado que o usuário com id "456" deseja remover o produto com SKU "200" da sua wishlist
    Quando o usuário envia a requisição para remover o produto da wishlist
    Então a resposta da remoção deve ter o status 204

  Cenário: Remover um produto de um usuário inexistente
    Dado que o usuário com id "100" deseja remover o produto com SKU "200" da sua wishlist
    Quando o usuário envia a requisição para remover o produto da wishlist
    Então a resposta da remoção deve ter o status 404
    E a resposta deve conter o erro "Wishlist not found"

  Cenário: Remover um produto inexistente de um usuário existente
    Dado que o usuário com id "456" deseja remover o produto com SKU "300" da sua wishlist
    Quando o usuário envia a requisição para remover o produto da wishlist
    Então a resposta da remoção deve ter o status 404
    E a resposta deve conter o erro "Product not found"
