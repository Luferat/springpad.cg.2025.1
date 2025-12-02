# Apagando um Pad

Se o usuário logado é proprietário do "pad", o botão `[Apagar]` é exibido logo abaixo da visão do "pad".

Ao clicar no botão, aparece a mensagem de confirmação que, se confirmado, leva a rota `/apaga/{id}` que confirma a identidade do proprietário (via cookie) e marca `status.DEL` para o registro.

Se não é o proprietário ou o "pad" não existe, redireciona para "/".

Se apagou, redireciona para "/" com uma mensagem de sucesso.

 - [ ] Crie [`com.projetos.springpad.controller.pad.DeleteController`](#file-deletecontroller-java)
 - [ ] Adicione o atributo `HttpSession session` no método `home` de [`com.projetos.springpad.controller.HomeController`](#file-homecontroller-java)

