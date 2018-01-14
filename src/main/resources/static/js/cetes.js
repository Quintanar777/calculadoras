//Resultado inicial
var result1 = new Vue({
  el: '#result-1',
  data: {
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

//Resultado de primer re inversion
var result2 = new Vue({
  el: '#result-2',
  data: {
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

var formCetes = new Vue({
  el: '#form-cetes',
  data: {
  },
  methods: {
    //evento click boton calcular
    calcular: function(){
      if(validaCampos()){ //true si todos los campos son capturados
        console.log('calculando cetes...');

        var data = {}
        data["monto"] = $('#monto').val();
        data["plazo"] = $('#plazo').val();

        $.ajax({
            type: "POST",
		        contentType: "application/json",
		        url: "calc/cetes",
		        data: JSON.stringify(data),
		        dataType: 'json',
            success: function (data) {
                console.log(data);
                result1.interesBruto= data.interesBruto
                result1.inversionBonddia= data.inversionBonddia
                result1.inversionCetes= data.inversionCetes
                result1.isr= data.isr
                result1.montoTotal= data.montoTotal
                result1.noTitulosBonddia= data.noTitulosBonddia
                result1.noTitulosCetes= data.noTitulosCetes
                result1.tasaBrutaBonddia= data.tasaBrutaBonddia
                result1.tasaBrutaCetes= data.tasaBrutaCetes

                $('#result-1').show();
            }
        });

        //validar si sera con re inversión
        if ($('#check-reinvertir').is(':checked') ) {
          var data = {}
          data["monto"] = $('#monto').val();
          data["plazo"] = $('#plazo').val();

          //obtener los periodos
          var periodos = $('#myRange').val() / 28;
          console.log('periodos : ' + parseInt(periodos));
          data["periodos"] = parseInt(periodos);

          $.ajax({
              type: "POST",
  		        contentType: "application/json",
  		        url: "calc/cetes/reinvertir",
  		        data: JSON.stringify(data),
  		        dataType: 'json',
              success: function (data) {
                  console.log(data);
                  result2.interesBruto= data[data.length - 1].interesBruto
                  result2.inversionBonddia= data[data.length - 1].inversionBonddia
                  result2.inversionCetes= data[data.length - 1].inversionCetes
                  result2.isr= data[data.length - 1].isr
                  result2.montoTotal= data[data.length - 1].montoTotal
                  result2.noTitulosBonddia= data[data.length - 1].noTitulosBonddia
                  result2.noTitulosCetes= data[data.length - 1].noTitulosCetes
                  result2.tasaBrutaBonddia= data[data.length - 1].tasaBrutaBonddia
                  result2.tasaBrutaCetes= data[data.length - 1].tasaBrutaCetes
                  $('#result-2').show();
                  $('#labelReinvertir').show();
              }
          });

        }else{
          $('#result-2').hide();
          $('#labelReinvertir').hide();
        }

      }
    },
    changeReInvertir: function(){
      if ($('#check-reinvertir').is(':checked') ) {
        $('#div-reinvertir').show();
      }else{
        $('#div-reinvertir').hide();
      }
    }
  }
})

//funcion para validar campos obligatorios
function validaCampos() {
  var monto = $('#monto').val();
  var plazo = $('#plazo').val();
  if(isEmpty(monto)){
    $('#errorMonto').show();
    return false;
  }else{
    $('#errorMonto').hide();
  }
  if(isEmpty(plazo)){
    $('#errorPlazo').show();
    return false;
  }
  else{
    $('#errorPlazo').hide();
  }
  return true;
}


//slider
var slider = document.getElementById("myRange");
var output = document.getElementById("dias-inversion");
output.innerHTML = slider.value;

slider.oninput = function() {
  output.innerHTML = this.value;
}
