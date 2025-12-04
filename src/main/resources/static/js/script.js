/**
 * src/main/resources/static/js/script.js
 * JavaScript do layout.
 * Template com autenticação de usuário pelo Google.
 * Referências desta página: https://firebase.google.com/docs/build?hl=pt-br
 */

/**
 * Configuração: ação ao clicar no usuário logado:
 *  - Se vazio (''), faz logout do usuário
 *  - Se tem uma URL (Ex.: '/profile'), acessa
 */
const loggedUserAction = '';
// const loggedUserAction = '/profile';

/**
 * Configuração: ID do elemento que contém o avatar do usuário.
 * Altere conforme seu HTML.
 */
const userClickId = 'userLoginOut';

/**
 * Configuração: rota de persistência.
 * Rota da API/Backend que recebe o JSON com os dados do usuário quando o perfil é atualizado.
 * - Use um endpoint completo ou somente a rota. Ex.:
 *     - Endpoint completo → https://minhaapi.com/user/login
 *     - Somente a rota → /user/login ← Se o front-end está no mesmo domínio.
 *   DICA! Quando implementar seu back-end, não esqueça de implementar o endpoint abaixo usando o método POST.
 * - Se vazio (""), não envia os dados para a API/backend;
 * - Se "firebase", faz a persistência no projeto atual do Firebase Firestore, na coleção `Users`;
 */
// const apiLoginEndpoint = 'firebase';
const apiLoginEndpoint = '/owner/login';
// const apiLoginEndpoint = '';

/**
 * Configuração: rota de logout
 * Rota da API/Backend que recebe a requisição de logout do usuário quando ele sai do front-end.
 * Por exemplo, notifica o backend para deletar o cookie de sessão seguro (JWT).
 * - Use um endpoint completo ou somente a rota. Ex.:
 *     - Endpoint completo → https://minhaapi.com/user/logout
 *     - Somente a rota → /user/logout ← Se o front-end está no mesmo domínio.
 * - Se vazio (""), não envia os dados para a API/backend;
 */
const apiLogoutEndpoint = '/owner/logout';
// const apiLogoutEndpoint = '';

/**
 * Configuração: mostra logs das ações no console
 *  - Se true, mostra logs
 *  - Se false, oculta logs
 */
const showLogs = false;

/**************************************************************************
 * Não altere nada à partir daqui a não ser que saiba o que está fazendo! *
 **************************************************************************/

// Inicializa o Firebase e o Authentication
const app = firebase.initializeApp(firebaseConfig);
const auth = app.auth();

// Referência ao elemento HTML clicável
const userInOut = document.getElementById(userClickId);

// Adicione a class "is-logged" aos elementos da página que só são visíveis quando o usuário está logado.
const isLogged = document.querySelectorAll('.is-logged');

// Adicione a class "not-is-logged" aos elementos da página que só são visíveis quando não tem usuário logado.
const notIsLogged = document.querySelectorAll('.not-is-logged');

// Função para Login com Google usando Popup
const googleLogin = async () => {
    const provider = new firebase.auth.GoogleAuthProvider();
    try {
        // Abre o popup do Google para login
        await auth.signInWithPopup(provider);
        showLogs && console.log('Login com Google bem-sucedido!');
        // O estado de autenticação será atualizado pelo listener onAuthStateChanged abaixo
    } catch (error) {
        showLogs && console.error("Erro no login com Google:", error);
        alert('Erro ao fazer login. Verifique o console para mais detalhes.');
    }
};

const googleLogout = async () => {
    try {
        let backendLogoutSuccess = true;

        // 1. Notificar backend (se endpoint configurado)
        if (apiLogoutEndpoint) {
            try {
                const response = await fetch(apiLogoutEndpoint, {
                    method: 'POST',
                    credentials: 'include',
                    headers: {'Content-Type': 'application/json',},
                    body: JSON.stringify({ action: "logout" })
                });

                backendLogoutSuccess = response.ok;

                if (!response.ok) {
                    showLogs && console.warn('Backend logout falhou, mas continuando...');
                } else {
                    showLogs && console.log('Cookie removido pelo backend.');
                }
            } catch (err) {
                showLogs && console.warn('Erro ao comunicar com backend:', err);
                backendLogoutSuccess = false;
            }
        }

        // Logout do Firebase (independente do backend)
        try {
            await auth.signOut();
            showLogs && console.log('Logout do Firebase concluído.');
        } catch (err) {
            showLogs && console.error('Erro ao fazer signOut no Firebase:', err);
            return;
        }

        // 3. Redirecionar apenas se tudo (ou o essencial) deu certo
        if (backendLogoutSuccess || !apiLogoutEndpoint) {
            window.location.href = '/';
        } else {
            // Opcional: redirecionar mesmo assim, ou mostrar aviso
            showLogs && console.error('Logout parcial: sessão local limpa, mas cookie pode persistir.', err);
            window.location.href = '/';
        }

    } catch (error) {
        showLogs && console.error("Erro inesperado no logout:", error);
    }
};

// Função de Manipulação de Clique (Login/Página de Perfil)
const handleUserInOutClick = (event) => {
    // Evita que o <a> navegue imediatamente, pois vamos gerenciar isso no JavaScript
    event.preventDefault();

    // Obtém status do usuário
    const user = auth.currentUser;

    if (user) {
        // Usuário LOGADO clica no avatar
        if (loggedUserAction == '') {
            // Opção para fazer logout
            if (confirm("Tem certeza que deseja sair do aplicativo?")) {
                googleLogout()
            }
        } else {
            // Opção que redireciona para outra rota
            window.location.href = loggedUserAction;
        }
    } else {
        // Usuário DESLOGADO: Inicia o processo de login
        googleLogin();
    }
};

// Função para atualizar a interface
const updateUI = (user) => {
    if (user) {
        // Usuário LOGADO: Mostra o Avatar

        // Atualiza o elemento <img> com o avatar do usuário (photoURL)
        const avatarImg = `<img src="${user.photoURL || '/img/user.png'}" alt="${user.displayName || 'Avatar do Usuário'}" class="rounded-circle avatar-sm" referrerpolicy="no-referrer">`;

        // Atualiza o elemento <span> com nome do usuário (displayName)
        const loginSpan = `<span class="d-md-none ms-3">${user.displayName || 'Usuário logado'}</span>`;

        // Atualiza os dados do usuário no link
        userInOut.innerHTML = avatarImg + loginSpan;

        // Atualiza o link para a página de perfil
        if (loggedUserAction == '') {
            // Atualiza o link para o logout (embora o JS trate o clique)
            userInOut.href = '/logout';
            userInOut.title = `Fazer logout de ${user.displayName}`;
        } else {
            userInOut.href = loggedUserAction;
            userInOut.title = `Ver perfil de ${user.displayName}`;
        }

        // Mostra elementos quando usuário ESTÁ logado
        isLogged.forEach(element => {
            element.classList.remove('d-none');
            element.classList.add('d-block');
        });

        // Oculta elementos quando usuário ESTÁ logado
        notIsLogged.forEach(element => {
            element.classList.remove('d-block');
            element.classList.add('d-none');
        });

    } else {
        // Usuário DESLOGADO: Mostra a imagem padrão

        // Cria o elemento <img> (ícone)
        const icon = `<img src="/img/user.png" alt="Logue-se com o Google" class="rounded-circle avatar-sm">`;

        // Cria o elemento <span>
        const loginSpan = `<span class="d-md-none ms-3">Login com Google</span>`;

        // Adiciona o ícone ao avatar usuário
        userInOut.innerHTML = icon + loginSpan;

        // Atualiza o link para o login (embora o JS trate o clique)
        userInOut.href = '/login';
        userInOut.title = `Fazer login usando o Google`;

        // Oculta elementos quando usuário NÃO logado
        isLogged.forEach(element => {
            element.classList.remove('d-block');
            element.classList.add('d-none');
        });

        // Mostra elementos quando usuário NÃO logado
        notIsLogged.forEach(element => {
            element.classList.remove('d-none');
            element.classList.add('d-block');
        });
    }
};

/**
 * Função para persistir os dados em uma API
 * Envia os dados do usuário logado para a API/backend via JSON.
 */
const sendUserToBackend = async (user) => {
    const idToken = await user.getIdToken(true);

    showLogs && console.log(user);

    try {
        // Dados do usuário do Firebase Authentication a serem persistidos
        const userData = {
            uid: user.uid,
            displayName: user.displayName,
            email: user.email,
            photoURL: user.photoURL,
            // Converte as datas para UTC/ISO ao enviar para o backend
            createdAt: timestampToISO(user.metadata.createdAt),
            lastLoginAt: timestampToISO(user.metadata.lastLoginAt),
        };

        // A rota da API recebe os dados do usuário logado via JSON e POST e faz persistência
        const response = await fetch(apiLoginEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${idToken}`
            },
            body: JSON.stringify(userData)
        });

        if (response.ok) {
            showLogs && console.log('Dados do usuário enviados com sucesso para o backend');
        } else {
            showLogs && console.log('Erro ao enviar dados para o backend');
        }
    } catch (error) {
        showLogs && console.error('Erro ao enviar dados:', error);
    }

    // console.log(idToken, userData, JSON.stringify(userData), response.ok);
};

/**
 * Função para persistir os dados no Firebase Firestore
 * Envia os dados do usuário logado para a API/backend via JSON.
 */
const sendUserToFirestore = async (user) => {
    try {
        // Se a biblioteca Firestore não estiver carregada mostra erro
        if (typeof firebase.firestore !== 'function') {
            showLogs && console.error("Firestore não está inicializado. Certifique-se de que a biblioteca Firestore (ex: firebase-firestore.js) foi carregada.");
            return;
        }

        // Inicializa o Firestore
        const db = firebase.firestore();

        // Referência ao documento do usuário (document ID = user.uid) na coleção 'Users'
        const userDocRef = db.collection("Users").doc(user.uid);

        // Dados a serem persistidos no Firestore
        const userData = {
            uid: user.uid,
            displayName: user.displayName || null,
            email: user.email || null,
            photoURL: user.photoURL || null,
            // Converte as datas para UTC/ISO ao enviar para o backend
            createdAt: timestampToISO(user.metadata.createdAt) || null,
            lastLoginAt: timestampToISO(user.metadata.lastLoginAt) || null,
        };

        // Cria ou atualiza o documento.
        // { merge: true } garante que o documento seja atualizado se já existir,
        // preservando outros campos não especificados em 'userData'.
        await userDocRef.set(userData, { merge: true });

        showLogs && console.log('Dados do usuário persistidos com sucesso no Firestore (Coleção Users, Doc ID: ' + user.uid + ')');

    } catch (error) {
        showLogs && console.error('Erro ao persistir dados no Firestore:', error);
    }
}

/**
 * Converte um timestamp em milissegundos para o formato UTC 'YYYY-MM-DD HH:MM:SS'.
 * A data e hora retornadas representam o momento em UTC.
 * @param {number} timestamp_ms - O timestamp em milissegundos (ex: 1758895930030).
 * @returns {string} A string de data/hora formatada em UTC.
 */
const timestampToISO = (timestamp_ms) => {
    const date = new Date(Number(timestamp_ms));
    const year = date.getUTCFullYear();
    const month = (date.getUTCMonth() + 1).toString().padStart(2, '0');
    const day = date.getUTCDate().toString().padStart(2, '0');
    const hours = date.getUTCHours().toString().padStart(2, '0');
    const minutes = date.getUTCMinutes().toString().padStart(2, '0');
    const seconds = date.getUTCSeconds().toString().padStart(2, '0');
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
};

/**
 * Converte uma data no formato UTC para timestamp.
 * @param {StringUTC} isoString - Uma data no formato UTC (ex: 2025-11-22 10:24:39)
 * @returns {Number} A data convertida para timestamp.
 */
const isoToTimestamp = (isoString) => {
    return new Date(isoString.replace(' ', 'T') + 'Z').getTime();
};

// Oculta o menu ao clicar em um item, em telas menores
document.addEventListener('DOMContentLoaded', () => {
  const navLinks = document.querySelectorAll('#mynavbar .nav-link');
  const collapseElement = document.getElementById('mynavbar');
  navLinks.forEach(link => {
    link.addEventListener('click', () => {
      if (collapseElement.classList.contains('show')) {
        const collapse = new bootstrap.Collapse(collapseElement);
        collapse.hide();
      }
    });
  });
});

// Listener para o estado de autenticação
// Este listener é executado sempre que o estado do usuário (logado/deslogado) muda.
auth.onAuthStateChanged((user) => {
    updateUI(user);

    // Se usuário fez login, envia dados para o backend
    if (user && apiLoginEndpoint != '') {
        if (apiLoginEndpoint == 'firebase') {
            showLogs && console.log("Persistindo no Firebase.");
            sendUserToFirestore(user);
        } else {
            showLogs && console.log("Persistindo na API.");
            sendUserToBackend(user);
        }
    } else {
        showLogs && console.log("Persistência desligada!");
    }
});

// Adiciona o Event Listener ao elemento `userInOut`
userInOut.addEventListener('click', handleUserInOutClick);

// Fecha caixas de alerta após 5 segundos
const delayInMilliseconds = 5000;
document.addEventListener('DOMContentLoaded', function () {
    const alertElement = document.getElementById('baseMainAlert');
    if (alertElement) {
        const bsAlert = new bootstrap.Alert(alertElement);
        setTimeout(function () { bsAlert.close(); }, delayInMilliseconds);
    }
});

// Bloqueia reenvio do formulário ao atualizar a página
if (window.history.replaceState) {
    window.history.replaceState(null, null, window.location.href);
}