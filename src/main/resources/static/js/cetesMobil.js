var dataPeridos;
var showComparar = false;

//Resultado inicial
var resultMobil = new Vue({
  el: '#resultMobil',
  data: {
    montoInicial: "0.0",
    interesBruto: "0.0",
    inversionBonddia: "0.0",
    inversionCetes: "0.0",
    isr: "0.0",
    montoTotal: "0.0",
    noTitulosBonddia: "0.0",
    noTitulosCetes: "0.0",
    tasaBrutaBonddia: "0.0",
    tasaBrutaCetes: "0.0"
  }
})



//se crea el objeto del div de los campos de captura de Cetes

var formCetesMobil = new Vue({
  el: '#form-cetes-mobil',
  data: {
  },
  methods: {
    //evento click boton calcular
    calcularMobil: function(){

      $('#titulo28_mobil').hide();
      $('#titulo91_mobil').hide();
      $('#titulo182_mobil').hide();
      $('#titulo360_mobil').hide();


      if(validaCamposMobil()){ //true si todos los campos son capturados
        console.log('calculando cetes en Mobil...');

        var monto1 = $('#montoMobil').val();
        var monto = parseFloat(monto1.replace(/[^\d]/g, ''));
        var plazo = $('#plazoMobil').val();

        var data = {}
        data["monto"] = monto;
        data["plazo"] = $('#plazoMobil').val();

        $.ajax({
            type: "POST",
		        contentType: "application/json",
		        url: "calc/cetes",
		        data: JSON.stringify(data),
		        dataType: 'json',
            success: function (data) {
                console.log(data);

                if (plazo==28) { $('#titulo28_mobil').show(); }
                if (plazo==91) { $('#titulo91_mobil').show(); }
                if (plazo==182) { $('#titulo182_mobil').show(); }
                if (plazo==360) { $('#titulo360_mobil').show(); }


                resultMobil.montoInicial=data.montoInicial
                resultMobil.interesBruto= data.interesBruto
                resultMobil.inversionBonddia= data.inversionBonddia
                resultMobil.inversionCetes= data.inversionCetes
                resultMobil.isr= data.isr
                resultMobil.montoTotal= data.montoTotal
                resultMobil.noTitulosBonddia= data.noTitulosBonddia
                resultMobil.noTitulosCetes= data.noTitulosCetes
                resultMobil.tasaBrutaBonddia= data.tasaBrutaBonddia
                resultMobil.tasaBrutaCetes= data.tasaBrutaCetes

                $('#resultMobil').show();


            }
        });
      }
    }
  }
})


//funcion para validar campos obligatorios
function validaCamposMobil() {
  var monto1 = $('#montoMobil').val();
  var monto = parseFloat(monto1.replace(/[^\d]/g, ''));

  var plazo = $('#plazoMobil').val();
  if(isEmpty(monto)){
    $('#errorMontoMobil').show();
    return false;
  }else{
    if(monto >= 100 && monto <=10000000){ //debe ser entre 100 y 10 millones
      $('#errorRangoMontoMobil').hide();
    }else{
      $('#errorRangoMontoMobil').show();
      return false;
    }
    $('#errorMontoMobil').hide();
  }
  if(isEmpty(plazo)){
    $('#errorPlazoMobil').show();
    return false;
  }
  else{
    $('#errorPlazoMobil').hide();
  }
  return true;
}



function dosDecimales(n) {
  let t=n.toString();
  let regex=/(\d*.\d{0,2})/;
  return t.match(regex)[0];
}
