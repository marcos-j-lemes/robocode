# RoboCode

Atividade proposta para a prática de versionamento de código em grupo.

## Pontos trabalhados:

* **Branch** – Trabalho com diferentes ramificações do repositório.  
* **Versionamento** – Comandos utilizados para subir o projeto no GitHub.  
* **Colaboração** – Trabalho em equipe com separação de tarefas e uso de múltiplas branches.  

## Branches

> Foi criada uma branch principal para o desenvolvimento do robô. Em seguida, foram adicionadas novas branches para trabalhar em partes separadas, como `oficina` e `oficina-c11`.

* `main` – Branch principal de produção.  
* `estruturas` – Foram adicionadas as estruturas básicas do robô.  
* `oficina` – Branch responsável por desenvolver o robô, deixando-o mais forte.  
* `oficina-c11` – Nessa branch, foi adicionado outro robô, servindo também para realização de testes e como backup.  

## Desenvolvimento

Para realizar esse trabalho, foi bem tranquilo. A parte que mais tivemos dificuldade foi desenvolver o robô, tendo em vista que seria necessário entender o código de outras pessoas.

## Git: `Dificuldades`

> Um ponto que não priorizamos muito foi fazer commits separados, sendo feitos poucos commits, mas com grande quantidade de código.

Não tivemos muitas dificuldades. Pude aprender que o Git é muito mais do que apenas subir `linhas de código` para o GitHub. Ele nos permite viajar entre commits, o que é muito legal, desde voltar, restaurar até corrigir conflitos.

## Pontos interessantes

Comandos que usamos e aprendemos suas funções, sendo úteis para evitar perdas ou conflitos.

> Além dos convencionais para versionar o código.

1. `git restore .` – Usado para descartar alterações feitas em uma branch, em arquivos já versionados.  
2. `git reset --hard <hash>` – Usado para voltar a commits anteriores.  
3. `git merge --no-ff <branch>` – Usado para não mesclar duas branches diretamente, deixando o histórico e as ramificações mais claras.  
4. `git log --oneline --graph` – Para visualizar as ramificações das branches.  
5. `git checkout --theirs .` – Usado quando ocorrem conflitos, descartando as alterações de uma das branches.  
6. `git clean -fd` – Usado para remover arquivos não rastreados.  

> Comandos que foram vistos, mas não utilizados.

1. `git rebase` – Não chegamos a usar, mas é muito interessante sua função.  
2. `git stash` – Guarda as alterações, permitindo navegar entre as branches sem precisar dar `push`. E `git stash pop` é usado para recuperar as alterações.  

## Histórico dos commits:

`git log --oneline --graph`  
```
*   849cba2 (HEAD -> main, origin/main, origin/HEAD) Merge branch 'oficina' Junção das duas branch principais do projeto
|\  
| *   44001ca Merge branch 'oficina-c11' into oficina Junção das duas branches, sendo a branch oficina-c11 para realização de testes, juntamente com um backup.
| |\  
| | * dd67e99 (origin/oficina-c11) spire
| * | c33b054 (origin/oficina) Atualização Robô
| |/  
| * 069ab5a protótipo
| *   faf978a Merge branch 'estruturas' into oficina
| |\  
* | | eea6009 README.md
* | | 1f32b9e Note - Main
* | | a7c4599 README
* | | d097df2 README.md
| |/  
|/|  
* | dbf9862 (origin/estruturas) teste commit
|/  
* b5dccd8 teste
* 76d9327 Initial commit
```

## Grupo

* **Marcos J. Lemes**  
  * E-mail: `marcos.jlf@aluno.ifsc.edu.br`  

* **Bernardo Vieira de Souza**  
  * E-mail: `bernardo.v07@aluno.ifsc.edu.br`  
