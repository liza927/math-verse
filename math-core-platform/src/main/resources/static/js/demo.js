// ============================
// Глобальные настройки
// ============================
let baseUrl = localStorage.getItem('baseUrl') || 'http://localhost:8080';
document.getElementById('baseUrl').value = baseUrl;

let token = localStorage.getItem('token') || '';

function setBaseUrl() {
    baseUrl = document.getElementById('baseUrl').value.trim();
    localStorage.setItem('baseUrl', baseUrl);
    updateStatus('Базовый URL обновлён');
}

function updateStatus(msg, isError = false) {
    const el = document.getElementById('authStatus');
    if (!el) return;
    el.textContent = msg;
    el.style.color = isError ? '#b34a5a' : '#6b4a6b';
}

function updateTokenDisplay() {
    const display = document.getElementById('tokenDisplay');
    if (display) {
        display.textContent = token ? token.substring(0, 30) + '…' : '(нет)';
    }
    if (token) localStorage.setItem('token', token);
    else localStorage.removeItem('token');
}

updateTokenDisplay();

function copyToken() {
    if (!token) return;
    navigator.clipboard.writeText(token).then(() => {
        updateStatus('Токен скопирован в буфер');
    }).catch(() => {
        const ta = document.createElement('textarea');
        ta.value = token;
        document.body.appendChild(ta);
        ta.select();
        document.execCommand('copy');
        ta.remove();
        updateStatus('Токен скопирован (fallback)');
    });
}

function logout() {
    token = '';
    updateTokenDisplay();
    updateStatus('Вы вышли');
}

// ============================
// Вспомогательные функции
// ============================
function getHeaders(path) {
    const headers = { 'Content-Type': 'application/json' };
    if (token && path && !path.startsWith('/api/auth/')) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    return headers;
}

async function apiCall(method, path, body = null) {
    const url = baseUrl + path;
    const opts = {
        method,
        headers: getHeaders(path),
    };
    if (body) opts.body = JSON.stringify(body);

    try {
        const resp = await fetch(url, opts);
        const text = await resp.text();
        let data = text ? JSON.parse(text) : {};
        return { ok: resp.ok, status: resp.status, data };
    } catch (e) {
        return { ok: false, status: 0, data: { error: e.message } };
    }
}

// ============================
// Форматирование и Человекочитаемый Рендеринг
// ============================

/**
 * Преобразует любые данные (объекты, массивы, примитивы) в красивую HTML-структуру
 */
function buildHumanReadableHtml(data) {
    if (data === null || data === undefined) return '<span style="color: #888;">—</span>';

    if (typeof data !== 'object') {
        if (typeof data === 'boolean') {
            return data ? '<b style="color: #2e7d32;">Да / Успешно</b>' : '<b style="color: #c62828;">Нет / Ошибка</b>';
        }
        return `<span>${String(data)}</span>`;
    }

    // Если пришел массив
    if (Array.isArray(data)) {
        if (data.length === 0) return '<i>(Список пуст)</i>';

        // Если массив содержит объекты — выводим как таблицу
        if (typeof data[0] === 'object' && data[0] !== null) {
            const keys = Array.from(new Set(data.flatMap(item => Object.keys(item))));
            let html = '<table border="1" cellpadding="6" cellspacing="0" style="border-collapse: collapse; width: 100%; margin-top: 5px;">';
            html += '<tr style="background-color: #f2f2f2; text-align: left;">';
            keys.forEach(k => html += `<th style="padding: 6px; text-transform: capitalize;">${k}</th>`);
            html += '</tr>';

            data.forEach(row => {
                html += '<tr>';
                keys.forEach(k => {
                    html += `<td style="padding: 6px;">${buildHumanReadableHtml(row[k])}</td>`;
                });
                html += '</tr>';
            });
            html += '</table>';
            return html;
        }

        // Простой массив
        return `<ul>${data.map(item => `<li>${buildHumanReadableHtml(item)}</li>`).join('')}</ul>`;
    }

    // Если пришел объект — выводим его в виде списка карточкой / ключевых пар
    let html = '<div style="display: grid; gap: 6px; padding: 4px;">';
    for (const [key, value] of Object.entries(data)) {
        html += `
      <div style="display: flex; gap: 8px; border-bottom: 1px border-bottom: 1px dashed #eee; padding-bottom: 4px;">
        <strong style="min-width: 140px; color: #444; text-transform: capitalize;">${key}:</strong>
        <div>${buildHumanReadableHtml(value)}</div>
      </div>`;
    }
    html += '</div>';
    return html;
}

function renderResult(containerId, data, ok = true) {
    const el = document.getElementById(containerId);
    if (!el) return;

    if (!ok) {
        const errorMsg = data.message || data.error || 'Ошибка при выполнении запроса';
        el.innerHTML = `<div class="alert" style="color: #b34a5a; background-color: #fde8e8; padding: 10px; border-radius: 6px;">
      ❌ <strong>Ошибка:</strong> ${errorMsg}
    </div>`;
        return;
    }

    let resultHtml = '';
    if (data.message) {
        resultHtml += `<div class="alert alert-success" style="color: #2e7d32; background-color: #e8f5e9; padding: 10px; border-radius: 6px; margin-bottom: 8px;">
      ✅ ${data.message}
    </div>`;
    }

    resultHtml += buildHumanReadableHtml(data);
    el.innerHTML = resultHtml;
}

function showTab(tabId) {
    document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
    document.getElementById('tab-' + tabId).classList.add('active');
    document.querySelectorAll('.tab button').forEach(b => b.classList.remove('active'));
    document.querySelector(`.tab button[data-tab="${tabId}"]`).classList.add('active');
}

document.querySelectorAll('.tab button').forEach(btn => {
    btn.addEventListener('click', () => {
        showTab(btn.dataset.tab);
    });
});

// ============================
// API методы
// ============================

// ---- Авторизация ----
async function register() {
    const email = document.getElementById('regEmail').value.trim();
    const password = document.getElementById('regPassword').value.trim();

    token = '';
    updateTokenDisplay();

    const result = await apiCall('POST', '/api/auth/register', { email, password });
    renderResult('authResult', result.data, result.ok);

    if (result.ok && result.data.token) {
        token = result.data.token;
        updateTokenDisplay();
        updateStatus('Регистрация успешна! Токен сохранён');
    } else if (!result.ok) {
        updateStatus(result.data.message || result.data.error || 'Ошибка регистрации', true);
    }
}

async function login() {
    const email = document.getElementById('loginEmail').value.trim();
    const password = document.getElementById('loginPassword').value.trim();

    const result = await apiCall('POST', '/api/auth/login', { email, password });
    renderResult('authResult', result.data, result.ok);

    if (result.ok && result.data.token) {
        token = result.data.token;
        updateTokenDisplay();
        updateStatus('Вход выполнен');
    } else if (!result.ok) {
        updateStatus(result.data.message || result.data.error || 'Ошибка входа', true);
    }
}

async function loginTeacher() {
    const result = await apiCall('POST', '/api/auth/login', {
        email: 'teacher@mathverse.com',
        password: 'teacher123'
    });
    renderResult('authResult', result.data, result.ok);

    if (result.ok && result.data.token) {
        token = result.data.token;
        updateTokenDisplay();
        updateStatus('Вход как учитель');
    } else if (!result.ok) {
        updateStatus(result.data.message || result.data.error || 'Ошибка входа учителя', true);
    }
}

// ---- Темы ----
async function createTopic() {
    const name = document.getElementById('topicName').value.trim();
    const result = await apiCall('POST', '/api/teacher/topics', { name });
    renderResult('topicsResult', result.data, result.ok);
}

async function getTopics() {
    const result = await apiCall('GET', '/api/topics');
    renderResult('topicsResult', result.data, result.ok);
}

// ---- Шаблоны ----
async function createTemplate() {
    const body = {
        topicId: parseInt(document.getElementById('tplTopicId').value) || 0,
        operation: document.getElementById('tplOperation').value,
        complexity: parseInt(document.getElementById('tplComplexity').value) || 5,
        generationParam: document.getElementById('tplParam').value
    };
    const result = await apiCall('POST', '/api/teacher/task-templates', body);
    renderResult('templatesResult', result.data, result.ok);
}

async function getTemplates() {
    const result = await apiCall('GET', '/api/task-templates');
    renderResult('templatesResult', result.data, result.ok);
}

// ---- Попытки ----
async function startAttempt() {
    const templateId = parseInt(document.getElementById('attemptTemplateId').value) || 0;
    const result = await apiCall('POST', '/api/attempts/start', { taskTemplateId: templateId });
    renderResult('attemptsResult', result.data, result.ok);
    if (result.ok && result.data.attemptId) {
        document.getElementById('attemptId').value = result.data.attemptId;
    }
}

async function submitAnswer() {
    const attemptId = parseInt(document.getElementById('attemptId').value) || 0;
    const studentAnswer = document.getElementById('studentAnswer').value.trim();
    const result = await apiCall('POST', '/api/attempts/submit', { attemptId, studentAnswer });

    const container = document.getElementById('attemptsResult');

    if (result.ok && result.data.correct === false) {
        const hint = `
      <div class="alert" style="margin-top: 10px; background-color: #fff3cd; border: 1px solid #ffeeba; padding: 12px; border-radius: 6px;">
        <h4 style="margin: 0 0 6px 0; color: #856404;">❌ Неверный ответ</h4>
        <p style="margin: 0; color: #856404; line-height: 1.4;">
          💡 <strong>Подсказка:</strong> проверьте порядок элементов и знаки.<br>
          При транспонировании строки матрицы становятся столбцами.<br>
          Внимательно перечитайте условие задачи.
        </p>
      </div>
    `;
        container.innerHTML = buildHumanReadableHtml(result.data) + hint;
    } else {
        renderResult('attemptsResult', result.data, result.ok);
    }
}

async function getStats() {
    const result = await apiCall('GET', '/api/attempts/stats');
    renderResult('attemptsResult', result.data, result.ok);
}

// ---- Дашборд ----
async function getDashboard() {
    const result = await apiCall('GET', '/api/teacher/dashboard');
    renderResult('dashboardResult', result.data, result.ok);
}

// Инициализация
if (token) updateStatus('Токен загружен из localStorage');