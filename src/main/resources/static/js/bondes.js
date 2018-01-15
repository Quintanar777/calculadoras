//se crea el objeto del div de los campos de captura de Cetes

var formCetes = new Vue({
  el: '#form-bondes',
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
		        url: "calc/bondes",
		        data: JSON.stringify(data),
		        dataType: 'json',
            success: function (data) {
                console.log(data);
                $('#tableResult').html(data.tabla);
            }
        });

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
        arrayCetes.push(parseFloat(result1.inversionCetes.replace(',','')));
        arrayCetes.push(parseFloat(result3.inversionCetes.replace(',','')));
        arrayCetes.push(parseFloat(result5.inversionCetes.replace(',','')));
        arrayCetes.push(parseFloat(result7.inversionCetes.replace(',','')));

        //BONDDDIA
        arrayBonddia.push(parseFloat(result1.inversionBonddia.replace(',','')));
        arrayBonddia.push(parseFloat(result3.inversionBonddia.replace(',','')));
        arrayBonddia.push(parseFloat(result5.inversionBonddia.replace(',','')));
        arrayBonddia.push(parseFloat(result7.inversionBonddia.replace(',','')));

        var datCetes = {
          name: 'Inversión Cetes',
          data: arrayCetes
        }
        dataSeries.push(datCetes);
        var datBonddia = {
          name: 'Inversión Bonddia',
          data: arrayBonddia
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
            arrayCetes.push(parseFloat(dataPeridos[i].inversionCetes.replace(',','')));
            arrayBonddia.push(parseFloat(dataPeridos[i].inversionBonddia.replace(',','')));
          }
          var datCetes = {
            name: 'Inversión Cetes',
            data: arrayCetes
          }
          dataSeries.push(datCetes);
          var datBonddia = {
            name: 'Inversión Bonddia',
            data: arrayBonddia
          }
          dataSeries.push(datBonddia);

        }else{//graficar solo el resultado inicial
          cat.push('Periodo 1'); //Categoria
          var datCetes = {
            name: 'Inversión Cetes',
            data: [parseFloat(result1.inversionCetes.replace(',',''))]
          }
          dataSeries.push(datCetes);
          var datBonddia = {
            name: 'Inversión Bonddia',
            data: [parseFloat(result1.inversionBonddia.replace(',',''))]
          }
          dataSeries.push(datBonddia);
        }
      }

      Highcharts.chart('chart', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Inversión Cetes/ Bonddia'
        },
        xAxis: {
            categories: cat
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
    if(monto > 1 && monto <=10000000){ //debe ser entre 1 y 10 millones
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
