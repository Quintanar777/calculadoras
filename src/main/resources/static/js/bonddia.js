//div resultados
var result = new Vue({
  el: '#result-1',
  data: {
    inversionInicial: "0.00",
    inversionFinal: "0.00",
    remanente: "0.00"
  },
  methods: {
    graficar: function(){
      $('#modalGrafica').modal()


      Highcharts.chart('chart', {
        chart: {
            type: 'bar'
        },
        title: {
            text: 'Inversión Bonddia'
        },
        xAxis: {
            type: 'category'
        },
        yAxis: {
            min: 0,
            title: {
                text: 'Inversión'
            }
        },
        tooltip: {
            pointFormat: '<span style="color:{series.color}">{series.name}</span>: <b>{point.y}</b><br/>',
            shared: true
        },
        plotOptions: {
            column: {
                stacking: 'percent'
            }
        },
        credits: {
          enabled: false
        },
        series:[
          {
            name: 'Inversion Inicial',
            data: [parseFloat(this.inversionInicial.replace(',',''))]
          },
          {
            name: 'Inversion Final',
            data: [parseFloat(this.inversionFinal.replace(',',''))]
          }
        ]
    });
    }
  }
})


var formBondes = new Vue({
  el: '#form-bonddia',
  data: {

  },
  methods: {
    //evento click boton calcular
    calcular: function(){

      if(validaCampos()){ //true si todos los campos son capturados
        console.log('calculando bondes...');

        var data = {}
        data["monto"] = $('#monto').val();
        data["plazo"] = $('#plazo').val();

        $.ajax({
            type: "POST",
		        contentType: "application/json",
		        url: "calc/bonddia",
		        data: JSON.stringify(data),
		        dataType: 'json',
            success: function (data) {
              result.inversionInicial = data.inversionInicial
              result.inversionFinal = data.inversionFinal
              result.remanente = data.remanente
              $('#result-1').show()
            }
        });
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
    if(monto >= 100 && monto <=10000000){ //debe ser entre 100 y 10 millones
      $('#errorRangoMonto').hide();
    }else{
      $('#errorRangoMonto').show();
      return false;
    }
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
