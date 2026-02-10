// ====== CONFIG ======
//const SOAP_URL = "http://localhost:8080/services/empleados";
const SOAP_URL = "/services/empleados";

// ====== UI helpers ======
const $ = (id) => document.getElementById(id);

function showAlert(type, msg) {
  const el = $("alert");
  el.classList.remove("hidden");
  el.className = "mb-4 rounded-lg border p-3 text-sm";
  if (type === "success") el.classList.add("border-green-300", "bg-green-50", "text-green-800");
  if (type === "error") el.classList.add("border-red-300", "bg-red-50", "text-red-800");
  if (type === "info") el.classList.add("border-slate-200", "bg-slate-50", "text-slate-700");
  el.textContent = msg;
}

function setLoading(isLoading) {
  $("loading").classList.toggle("hidden", !isLoading);
  $("btnRefrescar").disabled = isLoading;
  $("btnRegistrar").disabled = isLoading;
}

// ====== SOAP builders ======
function soapEnvelope(bodyXml) {
  return `
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
  <soapenv:Header/>
  <soapenv:Body>
    ${bodyXml}
  </soapenv:Body>
</soapenv:Envelope>`.trim();
}

//* El namespace real (xmlns:ser=...) depende del WSDL.

function buildListarEmpleadosRequest() {
  return soapEnvelope(`<ser:listarEmpleadosVsBD xmlns:ser="http://service.soapempleados.demo.com/"/>`);
}


function xmlEscape(str) {
  if (str === null || str === undefined) return "";
  return String(str)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;")
    .replaceAll("'", "&apos;");
}

function buildRegistrarEmpleadoRequest(emp) {
  return soapEnvelope(`
<ser:registrarEmpleado xmlns:ser="http://service.soapempleados.demo.com/">
  <empleado>
    <id>0</id>
    <dni>${xmlEscape(emp.dni)}</dni>
    <nombres>${xmlEscape(emp.nombres)}</nombres>
    <apellidos>${xmlEscape(emp.apellidos)}</apellidos>
    <direccion>${xmlEscape(emp.direccion)}</direccion>
    <email>${xmlEscape(emp.email)}</email>
    <celular>${xmlEscape(emp.celular)}</celular>
    <area>${xmlEscape(emp.area)}</area>
  </empleado>
</ser:registrarEmpleado>
  `.trim());
}

// ====== SOAP call ======
async function callSoap(xml) {
  const res = await fetch(SOAP_URL, {
    method: "POST",
    headers: {
      "Content-Type": "text/xml; charset=utf-8",
      // "SOAPAction": "" // si tu servidor lo exige, descomenta
    },
    body: xml
  });

  const text = await res.text();
  if (!res.ok) {
    throw new Error(`HTTP ${res.status}\n${text}`);
  }
  return text;
}

// ====== XML parse helpers ======
function parseXml(xmlText) {
  const parser = new DOMParser();
  const doc = parser.parseFromString(xmlText, "text/xml");

  // si hay parse error
  const pe = doc.getElementsByTagName("parsererror");
  if (pe && pe.length) {
    throw new Error("Respuesta XML inválida: " + pe[0].textContent);
  }
  return doc;
}

function textOf(parent, tagName) {
  const el = parent.getElementsByTagName(tagName)[0];
  return el ? el.textContent : "";
}

/**
 * CXF suele devolver lista como:
 * <listarEmpleadosResponse>
 *   <return> <id>..</id> ... </return>
 *   <return> ... </return>
 * </listarEmpleadosResponse>
 */
function parseEmpleadosFromListResponse(xmlText) {
  const doc = parseXml(xmlText);
  const returns = Array.from(doc.getElementsByTagName("return"));

  return returns.map(r => ({
    id: textOf(r, "id"),
    dni: textOf(r, "dni"),
    nombres: textOf(r, "nombres"),
    apellidos: textOf(r, "apellidos"),
    direccion: textOf(r, "direccion"),
    email: textOf(r, "email"),
    celular: textOf(r, "celular"),
    area: textOf(r, "area"),
  }));
}

function renderEmpleados(rows) {
  const tbody = $("tbodyEmpleados");
  tbody.innerHTML = "";

  if (!rows.length) {
    tbody.innerHTML = `
      <tr>
        <td colspan="7" class="px-3 py-4 text-center text-slate-500">Sin registros</td>
      </tr>`;
    return;
  }

  for (const e of rows) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td class="px-3 py-2">${e.id}</td>
      <td class="px-3 py-2">${e.dni}</td>
      <td class="px-3 py-2">${e.nombres}</td>
      <td class="px-3 py-2">${e.apellidos}</td>
      <td class="px-3 py-2">${e.area}</td>
      <td class="px-3 py-2">${e.email}</td>
      <td class="px-3 py-2">${e.celular}</td>
    `;
    tbody.appendChild(tr);
  }
}

// ====== Actions ======
async function listarEmpleados() {
  try {
    setLoading(true);
    const xmlReq = buildListarEmpleadosRequest();
    const xmlRes = await callSoap(xmlReq);
    const empleados = parseEmpleadosFromListResponse(xmlRes);
    renderEmpleados(empleados);
    showAlert("info", `OK: ${empleados.length} empleado(s) cargados.`);
  } catch (err) {
    console.error(err);
    showAlert("error", "Error al listar: " + (err.message || err));
  } finally {
    setLoading(false);
  }
}

async function registrarEmpleado(evt) {
  evt.preventDefault();

  const emp = {
    dni: $("dni").value.trim(),
    nombres: $("nombres").value.trim(),
    apellidos: $("apellidos").value.trim(),
    direccion: $("direccion").value.trim(),
    email: $("email").value.trim(),
    celular: $("celular").value.trim(),
    area: $("area").value.trim()
  };

  // validación mínima
  if (!emp.dni || emp.dni.length !== 8) {
    showAlert("error", "DNI debe tener 8 dígitos.");
    return;
  }

  try {
    setLoading(true);
    const xmlReq = buildRegistrarEmpleadoRequest(emp);
    await callSoap(xmlReq);
    showAlert("success", "Empleado registrado. Refrescando listado...");
    $("formEmpleado").reset();
    await listarEmpleados();
  } catch (err) {
    console.error(err);
    showAlert("error", "Error al registrar: " + (err.message || err));
  } finally {
    setLoading(false);
  }
}

// ====== Wire up ======
$("btnRefrescar").addEventListener("click", listarEmpleados);
$("formEmpleado").addEventListener("submit", registrarEmpleado);

// auto-load
listarEmpleados();
