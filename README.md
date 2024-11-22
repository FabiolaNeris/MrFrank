# Monitoramento de Dispositivos e Otimização de Consumo de Energia

## Descrição do Projeto

Este é um aplicativo Android desenvolvido para monitorar os dispositivos conectados em uma residência, com foco na otimização do consumo de energia. Através do monitoramento do uso dos dispositivos, o aplicativo fornecerá dados para um sistema de aprendizado de máquina que ajudará a otimizar o uso de energia de cada aparelho, promovendo práticas mais sustentáveis e eficientes no consumo de energia.

Atualmente, o aplicativo conta com funcionalidades para:
- Inserir, editar e remover dispositivos conectados no cômodo **Sala de Estar**.
- Armazenar dados de dispositivos no Firebase Firestore.
- Realizar autenticação de usuários via Firebase Auth.

## Tecnologias Utilizadas

### Firebase Authentication (Firebase Auth)
A autenticação de usuários é feita através do **Firebase Auth**, permitindo que apenas usuários autenticados acessem os dados de seus dispositivos. O Firebase Auth oferece uma solução simples e segura para implementar o login no aplicativo, garantindo o controle de acesso.

### Firebase Firestore
Os dados dos dispositivos (como descrição, marca e voltagem) são armazenados no **Firebase Firestore**, um banco de dados NoSQL em tempo real. O Firestore é utilizado para:
- Salvar e gerenciar informações sobre os dispositivos.
- Realizar operações de leitura, atualização e exclusão de dados de dispositivos de forma eficiente e escalável.

## Funcionalidades

- **Inserção de dispositivos**: O usuário pode adicionar novos dispositivos ao cômodo "Sala de Estar", fornecendo informações como descrição, marca e voltagem.
- **Edição de dispositivos**: O usuário pode editar as informações dos dispositivos existentes.
- **Remoção de dispositivos**: O usuário pode remover dispositivos do Firestore.

## Protótipo

O design do aplicativo foi inicialmente projetado no **Figma** para fornecer uma visão clara e intuitiva da interface do usuário. O protótipo inclui telas de inserção, edição e visualização dos dispositivos conectados, com foco na simplicidade e usabilidade. Você pode visualizar o protótipo completo através do link abaixo:

[Visualizar Protótipo no Figma](https://www.figma.com/design/ABYTiaEOOxR7MyrpSGWMdx/Mr.Frank---energia?node-id=0-1&t=XoZzG0PQHg4okuSE-1)

## Instruções de Build e Execução

### Requisitos
- **Android Studio** (última versão recomendada)
- **SDK do Android** (versão 35 ou superior)
- **Conta no Firebase**: É necessário configurar o Firebase para autenticação e Firestore.

### Passos para Build
1. Clone o repositório:
   ```bash
   git clone https://github.com/FabiolaNeris/MrFrank.git
2. Abra o projeto no Android Studio.

3. Configure o Firebase no projeto:

4. Vá até Firebase Console, crie um novo projeto e adicione o seu aplicativo Android.

5. Habilite o Firebase Authentication (para login do usuário) e o Firestore (para armazenamento de dados).

6. Baixe o arquivo google-services.json e coloque-o na pasta app/ do projeto.

7. No build.gradle do projeto, verifique se as dependências do Firebase estão configuradas corretamente:

8. Sincronize o projeto com as dependências.

9. Execute o aplicativo no dispositivo ou emulador Android.

## Integrantes do projeto
* Fabiola **RM** 552715 **TURMA** 2TDSPB

* Rafael **RM** 553934 **TURMA** 2TDSPB

* Carlos **RM** 553597 **TURMA** 2TDSPR
 
