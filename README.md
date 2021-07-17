# sistema-distribuido-arquivos

 UNIVERSIDADE FEDERAL DE SERGIPE
                                  SISTEMA DE INFORMAÇÃO - ITABAIANA - 2021.2

                                    OLÁ, SEJA-BEM VIDO AO MANUAL DO SISTEMA

                                        Docente...: José Aélio Oliveira Júnior
                                        Discentes.: Erick Oliveira Santos
                                                    Gilson Caio Pinheiro Sobrinho
                                                    Luiz Carlos Costa Moitinho

1 - Objetivo
	O objetivo deste projeto foi desenvolver um sistema que simulasse a implementação de um conjunto de aplicações de um sistema de arquivos distribuído, porém, em uma única máquina local. Nesta aplicação é possível inicializar diversos servidores de arquivos, que podem aceitar solicitações de busca e envio de arquivos de um servidor principal e de clientes, respectivamente. Um servidor principal então pode aceitar requisições de um cliente para que busque um arquivo nos vários servidores de arquivos disponíveis e este retorna ao cliente uma lista contendo as informações necessárias para baixar o arquivo de qualquer um dos servidores de arquivo que possuam o arquivo desejado. Com as informações necessárias, através de uma interface gráfica, o cliente pode escolher de qual dos servidores de arquivo deseja baixar o mesmo, podendo escolher o local em que deseja baixar o arquivo no seu computador e acompanhar o progresso do download em tempo real. A aplicação utiliza tecnologias como sockets (para comunicação cliente - servidor e download de arquivos) e multicast (para busca de arquivo nos servidores de arquivo)

2 - Passo a passo da execução
	Para executar o projeto, iremos primeiramente iniciar instâncias dos servidores de arquivos, iniciar o servidor principal e o cliente. O projeto foi construído usando a IDE Netbeans, caso seja utilizada, basta abrir o projeto com a IDE e os arquivos já estarão em seus devidos lugares. Caso outra IDE (Eclipse) seja utilizada, basta copiar os pacotes dentro da pasta src/ para dentro de um novo projeto, e a pasta files/ para a pasta raiz do seu projeto.
        
        1. Para executar os servidores de arquivos, basta rodar o arquivo FileServer.java (atualmente o projeto está configurado para atender até 3 instâncias).
        2. Execute uma vez o arquivo MainServer.java para executar o servidor principal.
        3. Execute uma vez o arquivo Application.java para executar a interface gráfica do cliente.

3 - Simulação dos servidores locais
	Para que o projeto funcionasse localmente, foi feita uma separação de arquivos, onde cada pasta dentro da pasta files/ representa os arquivos que um servidor de arquivos possui na sua base. Se um servidor de arquivos estiver utilizando a porta 9001, significa que este servidor considerará que os seus arquivos são os da pasta files/9001/. Esse método foi utilizado para que pudéssemos fazer com que cada servidor de arquivos, mesmo estando no mesmo computador, possuíssem arquivos diferentes para o propósito de realizar testes mais realistas.


4 - Tutorial de uso

- Busque o arquivo
Ao inicializar a classe Application.java será exibido uma janela contendo um campo de pesquisa e um botão para confirmar a busca. Dito isso, digite um nome de um arquivo que pretende realizar uma busca, por exemplo: teste.txt, pressione o botão buscar e aguarde até que o sistema liste os servidores encontrados que possuem o arquivo teste.txt, o processo deve demorar até 10s. 


- Selecione arquivo de um servidor
Após realizar a busca por um arquivo, caso o mesmo seja localizado em algum servidor deverá ser listado em uma tabela com os seus respectivos dados: IP, porta e tamanho em Bytes e um botão para baixar. Caso nenhum servidor seja encontrado, uma mensagem de aviso será exibido em tela.
Com os servidores listados em tela, selecione uma linha da tabela clicando uma vez sobre a mesma, embora seja possível selecionar mais de uma linha, o sistema está configurado para baixar um arquivo por servidor. Se mais de uma linha for selecionada, apenas será baixado o arquivo referente ao servidor da última linha.

- Baixe e salve arquivo
Com a linha selecionada clique no botão baixar, uma janela será exibida para que se possa escolher a pasta em que o arquivo será salvo. Com a pasta selecionada, clique em Abrir. O processo de download entre o cliente e o servidor de arquivos será iniciado. Quando o download for concluído, 
