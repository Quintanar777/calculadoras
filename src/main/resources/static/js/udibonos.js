//Resultado inicial
var result1 = new Vue({
  el: '#result-1',
  data: {
    interesBruto: "0.0",
    inversionBonos: "0.0",
    inversionCetes: "0.0",
    isr: "0.0",
    montoTotal: "0.0",
    noTitulosBonos: "0.0",
    noTitulosCetes: "0.0",
    tasaBrutaBonos: "0.0",
    tasaBrutaCetes: "0.0",
    corteCupon: "0.0",
    impuesteCorteCupon: "0.0",
    montoBonddia: "0.0",
    remanentes: "0.0"
  }
})

var formBonos = new Vue({
  el: '#form-udibonos',
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
		        url: "calc/udibonos",
		        data: JSON.stringify(data),
		        dataType: 'json',
            success: function (data) {
                dataBondes = data;
                // $('#tableResult').html(data.tabla);

                result1.noTitulosCetes = data.result[data.result.length-6].titulosCetes
                result1.tasaBrutaCetes = data.result[data.result.length-6].tasaCetes
                result1.noTitulosBonos = data.result[data.result.length-6].tituloBonos
                result1.tasaBrutaBonos = data.result[data.result.length-6].tasaBonos
                result1.inversionCetes =  data.result[data.result.length-6].montoCetes
                result1.inversionBonos =  data.result[data.result.length-6].montoBonos
                result1.interesBruto = data.result[data.result.length-6].rendimiento
                result1.isr = data.result[data.result.length-6].isr
                result1.montoTotal = data.result[data.result.length-6].totalUltimo

                result1.corteCupon = data.result[data.result.length-6].corteCupon
                result1.impuesteCorteCupon = data.result[data.result.length-6].impuestoCorteCupon
                result1.montoBonddia = data.result[data.result.length-6].montoBonddia
                result1.remanentes = data.result[data.result.length-6].remanentes

                $('#result-1').show();
                $('#buttonGraficar').show();
            }
        });
      }
    }
  }
})

var graficar = new Vue({
  el: '#buttonGraficar',
  methods: {
    graficar: function(){

      $('#modalGrafica').modal()
      var cat = [];
      var dataSeries = [];

      cat = [];
      dataSeries = [];
      //crear categorias
      var  numCat = $('#plazo').val()/12
      for(var i=1; i<=numCat; i++){
        cat.push('Año ' + i);
      }

      var arrayInversion = [];
      for(var i=0; i<dataBondes.result.length;i+=12){
        arrayInversion.push(parseFloat(dataBondes.result[i].inversion.replace(/[^\d\.\-]/g,'')));
      }
      var inversion = {
        name: 'Inversion',
        data: arrayInversion,
        color: '#ed7d31',
        tooltip: {
          valuePrefix: '$',
        },
        pointPadding: 0.3,
        pointPlacement: 0.2
       }
      dataSeries.push(inversion);

      var arrayRendimiento = [];
      for(var i=11; i<dataBondes.result.length;i+=12){
        arrayRendimiento.push(parseFloat(dataBondes.result[i].rendimiento.replace(/[^\d\.\-]/g,'')));
      }
      var rendimiento = {
        name: 'Rendimiento',
        data: arrayRendimiento,
        color: '#4472c4',
        tooltip: {
          valuePrefix: '$',
        },
        pointPadding: 0.4,
        pointPlacement: 0.2,
        yAxis: 1
      }
      dataSeries.push(rendimiento);

      Highcharts.chart('chart', {
          chart: {
              type: 'column'
          },
          title: {
              text: 'Inversión Unibonos'
          },
          xAxis: {
              categories: cat
          },
          yAxis: [{
              min: 0,
              title: {
                  text: 'Inversion'
              }
          },
          {
            title: {
              text: 'Rendimiento'
            },
              opposite: true
          }],
          legend: {
            shadow: false
          },
          tooltip: {
              shared: true
          },
          plotOptions: {
            column: {
              grouping: false,
              shadow: false,
              borderWidth: 0
            }
          },
          credits: {
            enabled: false
          },
          series:dataSeries
      });

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
    if(monto > 99 && monto <=10000000){ //debe ser entre 100 y 10 millones
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
