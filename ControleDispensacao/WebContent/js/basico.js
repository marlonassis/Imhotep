if(navigator.appName.indexOf("Internet Explorer") >= 0 && window.location.toString().indexOf("recusaIExplorer") < 0) {
	window.location = "/imhotep/PaginasWeb/recusaIExplorer.hu";
}

//fonte: http://brunobrum.wordpress.com/2010/05/20/mascara-javascript-de-cnpj-e-cpf-no-mesmo-campo-do-formulario/
function mascaraMutuario(o,f){
    v_obj=o
    v_fun=f
    setTimeout('execmascara()',1)
}

//fonte: http://brunobrum.wordpress.com/2010/05/20/mascara-javascript-de-cnpj-e-cpf-no-mesmo-campo-do-formulario/
function execmascara(){
    v_obj.value=v_fun(v_obj.value)
}

//fonte: http://brunobrum.wordpress.com/2010/05/20/mascara-javascript-de-cnpj-e-cpf-no-mesmo-campo-do-formulario/
function cpfCnpj(v){
 
    //Remove tudo o que não é dígito
    v=v.replace(/\D/g,"")
 
    if (v.length <= 14) { //CPF
 
        //Coloca um ponto entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d)/,"$1.$2")
 
        //Coloca um ponto entre o terceiro e o quarto dígitos
        //de novo (para o segundo bloco de números)
        v=v.replace(/(\d{3})(\d)/,"$1.$2")
 
        //Coloca um hífen entre o terceiro e o quarto dígitos
        v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2")
 
    } else { //CNPJ
 
        //Coloca ponto entre o segundo e o terceiro dígitos
        v=v.replace(/^(\d{2})(\d)/,"$1.$2")
 
        //Coloca ponto entre o quinto e o sexto dígitos
        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3")
 
        //Coloca uma barra entre o oitavo e o nono dígitos
        v=v.replace(/\.(\d{3})(\d)/,".$1/$2")
 
        //Coloca um hífen depois do bloco de quatro dígitos
        v=v.replace(/(\d{4})(\d)/,"$1-$2")
 
    }
 
    return v
 
}

function exibicaoAlternadaItem(id) {
    if (document.getElementById(id).style.display == 'none') {
        document.getElementById(id).style.display = 'inline';
    }
    else {
        document.getElementById(id).style.display = 'none';
    }
}

function exibicaoAlternadaItemGuiado(id, janela, display) {
	alert('entrou');
	alert(janela);
    janela.document.getElementById(id).style.display = display;
    alert('saiu');
}

/**
 * Impede que o campo seja preenchido com caracteres que não sejam numéricos
 */
function apenasNumero(obj){
	valor = obj.value;
	valor = valor.replace(/\D/g,"");
	if(valor == '0')
		valor = null;
	obj.value = valor;
	return obj;
}  

function filtroQuantidadeLiberada(qtdSolicitada, obj){
	apenasNumero(obj);
	if(parseFloat(obj.value) > parseFloat(qtdSolicitada)){
		alert('A quantidade dispensada deve ser menor ou igual à quantidade solicitada');
		obj.value = '';
	}
}

function validaHoras(edit){
	alert(edit);
	li = new Array(':');
	liE = new Array(58);
	//somenteNumero(edit,ev,li,liE);
	if(edit.value.length == 2 || edit.value.length == 5)
		edit.value += ":";
}

function verifica_horas(obj){
	if(obj.value.length < 8)
		obj.value = '';
	else{
		hr = parseInt(obj.value.substring(0,2));
		mi = parseInt(obj.value.substring(3,5));
		se = parseInt(obj.value.substring(6,8));
		if((hr < 0 || hr > 23) || (mi < 0 || mi > 60) || (se < 0 || se > 60 )){
			obj.value = '';
			alert('Hora inválida');
		}
	}
}
	
function validaHora(obj){
	if(obj.value.lastIndexOf(":") == -1 && obj.value != ""){
		alert("Hora no formato inválido.");
		obj.value = "";
		return null;
	}
	var horaArray = obj.value.split(":");
	if(horaArray.size > 3 && obj.value != ""){
		alert("Hora no formato inválido." && obj.value != "");
		obj.value = "";
		return null;
	}
	var hora = horaArray[0];
	var minuto = horaArray[1];
	if((hora > 23 || hora < 0 || minuto > 59 || minuto < 0) && obj.value != ""){
		alert("Hora no formato inválido.");
		obj.value = "";
		return null;
	}

	if(minuto >= 0 && minuto <= 9 && minuto.toString().length == 1){
		operador = ":0";
	}else{
		operador = ":";
	}
	
	obj.value = hora.toString() + operador + minuto.toString();
}

function spinnerAdd(idComponente){
	var horaInicial = document.getElementById(idComponente).value;
	if(horaInicial == "" || horaInicial == null){
		horaInicial = "00:00";
	}
	var array = horaInicial.split(":");
	var hora = array[0];
	var minuto = array[1];
	minuto++;
	if(hora == 23 && minuto == 60){
		hora = 0;
		minuto = 0;
	}
	
	if(minuto == 60){
		minuto = 0;
		hora++;
	}

	var operador;
	
	if(minuto >= 0 && minuto <= 9 && minuto.toString().length == 1){
		operador = ":0";
	}else{
		operador = ":";
	}
	
	horaInicial = hora.toString() + operador + minuto.toString();
	document.getElementById(idComponente).value = horaInicial;
}

function spinnerRem(idComponente){
	var horaInicial = document.getElementById(idComponente).value;
	if(horaInicial == "" || horaInicial == null){
		horaInicial = "00:00";
	}
	var array = horaInicial.split(":");
	var hora = array[0];
	var minuto = array[1];
	minuto--;
	if(hora == 00 && minuto < 0){
		hora = 23;
		minuto = 59;
	}
	
	if(minuto == -1){
		minuto = 59;
		hora--;
	}

	var operador;
	
	if(minuto >= 0 && minuto <= 9 && minuto.toString().length == 1){
		operador = ":0";
	}else{
		operador = ":";
	}
	
	horaInicial = hora.toString() + operador + minuto.toString();
	document.getElementById(idComponente).value = horaInicial;
}

function apenasLetrasNumeros(obj){
	valor = obj.value;
	valor = valor.replace(/[^a-z0-9]$/i,"");
	valor = valor.toLowerCase();
	obj.value = valor;
	return obj;
}

function apenasNumeroSemZeroEsquerda(obj){
	apenasNumero(obj);
	valor = obj.value;
	b = valor.split('');

	i = 0;
	parou = false;
	while(!parou){
		if(b[i] != '0'){
			parou = true;
		}
		i++;
	}
	obj.value = valor.substring(i-1);
}

function avisoDelecao(){
	if(!confirm('Deseja realmente excluir?')){
		return false;
	}
	return true;
}
	
/**
 * Impede que o campo seja preenchido com caracteres que não sejam numéricos, mas permite a vírgula
 */
function apenasNumeroComVirgula(obj){
	v = obj.value;
	v=v.replace(/\D/g,""); //permite digitar apenas números
	v=v.replace(/[0-9]{12}/,v.substr(0,11)); //limita pra máximo 999.999.999,99
	v=v.replace(/(\d{1})(\d{8})$/,"$1.$2"); //coloca ponto antes dos últimos 8 digitos
	v=v.replace(/(\d{1})(\d{5})$/,"$1.$2"); //coloca ponto antes dos últimos 5 digitos
	v=v.replace(/(\d{1})(\d{1,2})$/,"$1,$2"); //coloca virgula antes dos últimos 2 digitos
	obj.value = v;
}

/**
 * valida se o dia informado é maior que 31
 */
function verificaDiaMes(obj){
	if(apenasNumero(obj).value  > 31){
		alert("O dia não pode ser maior que 31");
		obj.value = "";
	}
}
 

function verificaFormularioEntrada(){
	dataEntrada = document.getElementById('padraoTabView:vencimentoEntrada_input').value;
	valorEntrada = document.getElementById('padraoTabView:valorCompraEntrada').value;
	if(dataEntrada == '' && valorEntrada != ''){
		alert('Informe a data da entrada');
		return false;
	}
	if(dataEntrada != '' && valorEntrada == ''){
		alert('Informe o valor da entrada');
		return false;
	}
	return true;
}

function urlProjeto(){
	var url = location.href; //pega endereço que esta no navegador
	url = url.split("/"); //quebra o endeço de acordo com a / (barra)
	urlPagina = url[2]+'/'+url[3];
	return urlPagina;
}

//fonte: http://www.geradorcnpj.com/javascript-validar-cnpj.htm
function redirecionaPara(pagina){
	var url = location.href; //pega endereço que esta no navegador
	url = url.split("/"); //quebra o endeço de acordo com a / (barra)
	urlPagina = url[2]+'/'+url[3]+pagina;
	alert(urlPagina);
	location.href = urlPagina;
}

function validarCNPJ(cnpj) {
	 
    cnpj = cnpj.replace(/[^\d]+/g,'');
 
    if(cnpj == '') return false;
     
    if (cnpj.length != 14)
        return false;
 
    // Elimina CNPJs invalidos conhecidos
    if (cnpj == "00000000000000" ||
        cnpj == "11111111111111" ||
        cnpj == "22222222222222" ||
        cnpj == "33333333333333" ||
        cnpj == "44444444444444" ||
        cnpj == "55555555555555" ||
        cnpj == "66666666666666" ||
        cnpj == "77777777777777" ||
        cnpj == "88888888888888" ||
        cnpj == "99999999999999")
        return false;
         
    // Valida DVs
    tamanho = cnpj.length - 2
    numeros = cnpj.substring(0,tamanho);
    digitos = cnpj.substring(tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(0))
        return false;
         
    tamanho = tamanho + 1;
    numeros = cnpj.substring(0,tamanho);
    soma = 0;
    pos = tamanho - 7;
    for (i = tamanho; i >= 1; i--) {
      soma += numeros.charAt(tamanho - i) * pos--;
      if (pos < 2)
            pos = 9;
    }
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(1))
          return false;
           
    return true;
    
}

function validaCPF(obj){
	cpf = obj.value;
	cpf = cpf.replace(".", "");
	cpf = cpf.replace(".", "");
	cpf = cpf.replace("-", "");
	cpf = cpf.replace("___________", "");
    if(cpf == ''){
	  return false;
    }
    var numeros, digitos, soma, i, resultado;
    if (cpf.length < 11){
      alert('CPF inválido');
      obj.value = '';
      obj.focus();
      return false;
  	}
    numeros = cpf.substring(0,9);
    digitos = cpf.substring(9);
    soma = 0;
    for (i = 10; i > 1; i--)
          soma += numeros.charAt(10 - i) * i;
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(0)){
          alert('CPF inválido');
          obj.focus();
          obj.value = '';
          return false;
    }
    numeros = cpf.substring(0,10);
    soma = 0;
    for (i = 11; i > 1; i--)
          soma += numeros.charAt(11 - i) * i;
    resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
    if (resultado != digitos.charAt(1)){
        alert('CPF inválido');
        obj.value = '';
        obj.focus();
        return false;
    }
    return true;
}

function validaCNPJ(obj){
	cnpj = obj.value;
	cnpj = cnpj.replace(".", "");
	cnpj = cnpj.replace(".", "");
	cnpj = cnpj.replace("/", "");
	cnpj = cnpj.replace("-", "");
	cnpj = cnpj.replace("______________", "");
    var numeros, digitos, soma, i, resultado, pos, tamanho, digitos_iguais;
	if(cnpj == ''){
	  return false;
	}

    digitos_iguais = 1;
    if (cnpj.length < 14 && cnpj.length < 15){
      alert('CNPJ inválido');
      obj.focus();
      return false;
    }
    for (i = 0; i < cnpj.length - 1; i++)
      if (cnpj.charAt(i) != cnpj.charAt(i + 1)){
        digitos_iguais = 0;
        break;
      }
    if (!digitos_iguais){
      tamanho = cnpj.length - 2;
      numeros = cnpj.substring(0,tamanho);
      digitos = cnpj.substring(tamanho);
      soma = 0;
      pos = tamanho - 7;
      for (i = tamanho; i >= 1; i--){
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2)
          pos = 9;
      }
      resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
      if (resultado != digitos.charAt(0)){
    	alert('CNPJ inválido');
        obj.focus();
        return false;
      }
      tamanho = tamanho + 1;
      numeros = cnpj.substring(0,tamanho);
      soma = 0;
      pos = tamanho - 7;
      for (i = tamanho; i >= 1; i--){
        soma += numeros.charAt(tamanho - i) * pos--;
        if (pos < 2)
          pos = 9;
        }
        resultado = soma % 11 < 2 ? 0 : 11 - soma % 11;
        if (resultado != digitos.charAt(1)){
          alert('CNPJ inválido');
          obj.focus();
          return false;
        }
        return true;
      }
      else{
    	  alert('CNPJ inválido');
          obj.focus();
    	  return false;
      }
}

function validaEmail(obj)
{
  var txt = obj.value;
  if ((txt.length != 0) && ((txt.indexOf("@") < 1) || (txt.indexOf('.') < 7)))
  {
    alert('Email incorreto');
	obj.focus();
  }
}
