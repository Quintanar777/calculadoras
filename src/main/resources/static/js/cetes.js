var dataPeridos;
var showComparar = false;

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

//Resultado de comparar sin re inversion
var result3 = new Vue({
  el: '#result-3',
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
var result5 = new Vue({
  el: '#result-5',
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
var result7 = new Vue({
  el: '#result-7',
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

//Resultados re invertir comparar
var result4 = new Vue({
  el: '#result-4',
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
var result6 = new Vue({
  el: '#result-6',
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
var result8 = new Vue({
  el: '#result-8',
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
      $('#titulosComparar').hide();
      $('#result-3').hide();
      $('#result-5').hide();
      $('#result-7').hide();

      $('#result-4').hide();
      $('#result-6').hide();
      $('#result-8').hide();
      showComparar = false;

      if(validaCampos()){ //true si todos los campos son capturados
        console.log('calculando cetes...');
        $('#div-graficar').show();
        
        var monto1 = $('#monto').val();
        var monto = parseFloat(monto1.replace(/[^\d]/g, ''));


        var data = {}
  //      data["monto"] = $('#monto').val();
        data["monto"] = monto;
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
        	
            var monto1 = $('#monto').val();
            var monto = parseFloat(monto1.replace(/[^\d]/g, ''));

          var data = {}
    //      data["monto"] = $('#monto').val();
          data["monto"] = monto;
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
                  dataPeridos = data;
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
    comparar: function(){
      showComparar = true;
      if(validaCampos()){ //true si todos los campos son capturados
        console.log('comparando cetes...');
        $('#div-graficar').show();
        
        var monto1 = $('#monto').val();
        var monto = parseFloat(monto1.replace(/[^\d]/g, ''));


        var data = {}
        data["monto"] = monto;
        data["plazo"] = $('#plazo').val();

        $.ajax({
            type: "POST",
		        contentType: "application/json",
		        url: "calc/cetes/comparar",
		        data: JSON.stringify(data),
		        dataType: 'json',
            success: function (data) {
                console.log(data);
                //Resultado para plazo 28
                result1.interesBruto= data.comp28.interesBruto
                result1.inversionBonddia= data.comp28.inversionBonddia
                result1.inversionCetes= data.comp28.inversionCetes
                result1.isr= data.comp28.isr
                result1.montoTotal= data.comp28.montoTotal
                result1.noTitulosBonddia= data.comp28.noTitulosBonddia
                result1.noTitulosCetes= data.comp28.noTitulosCetes
                result1.tasaBrutaBonddia= data.comp28.tasaBrutaBonddia
                result1.tasaBrutaCetes= data.comp28.tasaBrutaCetes

                //Resultado para plazo 91
                result3.interesBruto= data.comp91.interesBruto
                result3.inversionBonddia= data.comp91.inversionBonddia
                result3.inversionCetes= data.comp91.inversionCetes
                result3.isr= data.comp91.isr
                result3.montoTotal= data.comp91.montoTotal
                result3.noTitulosBonddia= data.comp91.noTitulosBonddia
                result3.noTitulosCetes= data.comp91.noTitulosCetes
                result3.tasaBrutaBonddia= data.comp91.tasaBrutaBonddia
                result3.tasaBrutaCetes= data.comp91.tasaBrutaCetes

                //Resultado para plazo 182
                result5.interesBruto= data.comp182.interesBruto
                result5.inversionBonddia= data.comp182.inversionBonddia
                result5.inversionCetes= data.comp182.inversionCetes
                result5.isr= data.comp182.isr
                result5.montoTotal= data.comp182.montoTotal
                result5.noTitulosBonddia= data.comp182.noTitulosBonddia
                result5.noTitulosCetes= data.comp182.noTitulosCetes
                result5.tasaBrutaBonddia= data.comp182.tasaBrutaBonddia
                result5.tasaBrutaCetes= data.comp182.tasaBrutaCetes

                //Resultado para plazo 360
                result7.interesBruto= data.comp360.interesBruto
                result7.inversionBonddia= data.comp360.inversionBonddia
                result7.inversionCetes= data.comp360.inversionCetes
                result7.isr= data.comp360.isr
                result7.montoTotal= data.comp360.montoTotal
                result7.noTitulosBonddia= data.comp360.noTitulosBonddia
                result7.noTitulosCetes= data.comp360.noTitulosCetes
                result7.tasaBrutaBonddia= data.comp360.tasaBrutaBonddia
                result7.tasaBrutaCetes= data.comp360.tasaBrutaCetes

                $('#result-1').show();
                $('#result-3').show();
                $('#result-5').show();
                $('#result-7').show();
                $('#titulosComparar').show();
            }
        });

        //validar si sera con re inversión
        if ($('#check-reinvertir').is(':checked') ) {
        	
            var monto1 = $('#monto').val();
            var monto = parseFloat(monto1.replace(/[^\d]/g, ''));

        	
          var data = {}
          data["monto"] = monto;
          data["plazo"] = $('#plazo').val();

          //obtener los periodos
          var periodos = $('#myRange').val() / 28;
          console.log('periodos : ' + parseInt(periodos));
          data["periodos"] = parseInt(periodos);

          $.ajax({
              type: "POST",
  		        contentType: "application/json",
  		        url: "calc/cetes/comparar/reinvertir",
  		        data: JSON.stringify(data),
  		        dataType: 'json',
              success: function (data) {
                  console.log(data);
                  //Resultado para plazo 28
                  result2.interesBruto= data.responseCetes28[data.responseCetes28.length - 1].interesBruto
                  result2.inversionBonddia= data.responseCetes28[data.responseCetes28.length - 1].inversionBonddia
                  result2.inversionCetes= data.responseCetes28[data.responseCetes28.length - 1].inversionCetes
                  result2.isr= data.responseCetes28[data.responseCetes28.length - 1].isr
                  result2.montoTotal= data.responseCetes28[data.responseCetes28.length - 1].montoTotal
                  result2.noTitulosBonddia= data.responseCetes28[data.responseCetes28.length - 1].noTitulosBonddia
                  result2.noTitulosCetes= data.responseCetes28[data.responseCetes28.length - 1].noTitulosCetes
                  result2.tasaBrutaBonddia= data.responseCetes28[data.responseCetes28.length - 1].tasaBrutaBonddia
                  result2.tasaBrutaCetes= data.responseCetes28[data.responseCetes28.length - 1].tasaBrutaCetes

                  //Resultado para plazo 91
                  result4.interesBruto= data.responseCetes91[data.responseCetes91.length - 1].interesBruto
                  result4.inversionBonddia= data.responseCetes91[data.responseCetes91.length - 1].inversionBonddia
                  result4.inversionCetes= data.responseCetes91[data.responseCetes91.length - 1].inversionCetes
                  result4.isr= data.responseCetes91[data.responseCetes91.length - 1].isr
                  result4.montoTotal= data.responseCetes91[data.responseCetes91.length - 1].montoTotal
                  result4.noTitulosBonddia= data.responseCetes91[data.responseCetes91.length - 1].noTitulosBonddia
                  result4.noTitulosCetes= data.responseCetes91[data.responseCetes91.length - 1].noTitulosCetes
                  result4.tasaBrutaBonddia= data.responseCetes91[data.responseCetes91.length - 1].tasaBrutaBonddia
                  result4.tasaBrutaCetes= data.responseCetes91[data.responseCetes91.length - 1].tasaBrutaCetes

                  //Resultado para plazo 182
                  result6.interesBruto= data.responseCetes182[data.responseCetes182.length - 1].interesBruto
                  result6.inversionBonddia= data.responseCetes182[data.responseCetes182.length - 1].inversionBonddia
                  result6.inversionCetes= data.responseCetes182[data.responseCetes182.length - 1].inversionCetes
                  result6.isr= data.responseCetes182[data.responseCetes182.length - 1].isr
                  result6.montoTotal= data.responseCetes182[data.responseCetes182.length - 1].montoTotal
                  result6.noTitulosBonddia= data.responseCetes182[data.responseCetes182.length - 1].noTitulosBonddia
                  result6.noTitulosCetes= data.responseCetes182[data.responseCetes182.length - 1].noTitulosCetes
                  result6.tasaBrutaBonddia= data.responseCetes182[data.responseCetes182.length - 1].tasaBrutaBonddia
                  result6.tasaBrutaCetes= data.responseCetes182[data.responseCetes182.length - 1].tasaBrutaCetes

                  //Resultado para plazo 360
                  result8.interesBruto= data.responseCetes360[data.responseCetes360.length - 1].interesBruto
                  result8.inversionBonddia= data.responseCetes360[data.responseCetes360.length - 1].inversionBonddia
                  result8.inversionCetes= data.responseCetes360[data.responseCetes360.length - 1].inversionCetes
                  result8.isr= data.responseCetes360[data.responseCetes360.length - 1].isr
                  result8.montoTotal= data.responseCetes360[data.responseCetes360.length - 1].montoTotal
                  result8.noTitulosBonddia= data.responseCetes360[data.responseCetes360.length - 1].noTitulosBonddia
                  result8.noTitulosCetes= data.responseCetes360[data.responseCetes360.length - 1].noTitulosCetes
                  result8.tasaBrutaBonddia= data.responseCetes360[data.responseCetes360.length - 1].tasaBrutaBonddia
                  result8.tasaBrutaCetes= data.responseCetes360[data.responseCetes360.length - 1].tasaBrutaCetes

                  $('#result-2').show();
                  $('#result-4').show();
                  $('#result-6').show();
          //	        $('#result-8').show();
              }
          });
        }else{
          $('#result-2').hide();
          $('#result-4').hide();
          $('#result-6').hide();
          $('#result-8').hide();
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

//Div graficar
var divcharts =  new Vue({
  el: '#div-graficar',
  methods:{
    graficar: function(){
      $('#modalGrafica').modal()
      var cat = [];
      var dataSeries = [];

      //Graficar comparar?
      if(showComparar){
        var arrayCetes = [];
        var arrayBonddia = [];
        cat.push('Periodo CETES 28');
        cat.push('Periodo CETES 91');
        cat.push('Periodo CETES 182');
        cat.push('Periodo CETES 360');

        //Se agregan los data series de n periodos
        //CETES
        arrayCetes.push(parseFloat(result1.inversionCetes.replace(/[^\d\.\-]/g,'')));
        arrayCetes.push(parseFloat(result3.inversionCetes.replace(/[^\d\.\-]/g,'')));
        arrayCetes.push(parseFloat(result5.inversionCetes.replace(/[^\d\.\-]/g,'')));
        arrayCetes.push(parseFloat(result7.inversionCetes.replace(/[^\d\.\-]/g,'')));

        //BONDDDIA
        arrayBonddia.push(parseFloat(result1.interesBruto.replace(',','')));
        arrayBonddia.push(parseFloat(result3.interesBruto.replace(',','')));
        arrayBonddia.push(parseFloat(result5.interesBruto.replace(',','')));
        arrayBonddia.push(parseFloat(result7.interesBruto.replace(',','')));

        var datCetes = {
          name: 'Inversión Cetes',
          data: arrayCetes,
          color: '#ed7d31',
          tooltip: {
            valuePrefix: '$',
          },
          pointPadding: 0.3,
          pointPlacement: 0.2
        }
        dataSeries.push(datCetes);
        var datBonddia = {
          name: 'Rendimiento',
          data: arrayBonddia,
          color: '#4472c4',
          tooltip: {
            valuePrefix: '$',
          },
          pointPadding: 0.4,
          pointPlacement: 0.2,
          yAxis: 1
        }
        dataSeries.push(datBonddia);

      }else{
        //Graficar re inversión
        if($('#check-reinvertir').is(':checked')){
          var arrayCetes = [];
          var arrayBonddia = [];

          for(var i=0; i<dataPeridos.length;i++){
            //Se agregan las catagorias de n periodos
            cat.push('Periodo ' + (i+1));
            //Se agregan los data series de n periodos
            arrayCetes.push(parseFloat(dataPeridos[i].inversionCetes.replace(/[^\d\.\-]/g,'')));
            arrayBonddia.push(parseFloat(dataPeridos[i].interesBruto.replace(',','')));
          }
          var datCetes = {
            name: 'Inversion',
            data: arrayCetes,
            color: '#ed7d31',
            tooltip: {
              valuePrefix: '$',
            },
            pointPadding: 0.3,
            pointPlacement: 0.2
          }
          dataSeries.push(datCetes);
          var datBonddia = {
            name: 'Rendimiento',
            data: arrayBonddia,
            color: '#4472c4',
            tooltip: {
              valuePrefix: '$',
            },
            pointPadding: 0.4,
            pointPlacement: 0.2,
            yAxis: 1
          }
          dataSeries.push(datBonddia);

        }else{//graficar solo el resultado inicial
          cat.push('Periodo 1'); //Categoria
          var datCetes = {
            name: 'Inversion',
            data: [parseFloat(result1.inversionCetes.replace(/[^\d\.\-]/g,''))],
            color: '#ed7d31',
            tooltip: {
              valuePrefix: '$',
            },
            pointPadding: 0.3,
            pointPlacement: 0.2
           }
          dataSeries.push(datCetes);
          var datBonddia = {
            name: 'Rendimiento',
            data: [parseFloat(result1.interesBruto.replace(',',''))],
            color: '#4472c4',
            tooltip: {
              valuePrefix: '$',
            },
            pointPadding: 0.4,
            pointPlacement: 0.2,
            yAxis: 1
          }
          dataSeries.push(datBonddia);
        }
      }

      Highcharts.chart('chart', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Inversión Cetes'
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
  var monto1 = $('#monto').val();
  var monto = parseFloat(monto1.replace(/[^\d]/g, ''));
  	
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

//slider
var slider = document.getElementById("myRange");
var output = document.getElementById("dias-inversion");
output.innerHTML = slider.value;

slider.oninput = function() {
  output.innerHTML = this.value;
}
