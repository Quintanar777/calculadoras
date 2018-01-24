//var globales
var dataBondes;

var formBondes = new Vue({
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
                dataBondes = data;
                $('#tableResult').html(data.tabla);
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
      if($('#plazo').val() == 12){ //Un año
        cat = [];
        dataSeries = [];
        //crear categorias
        cat.push('Enero','Febrero','Marzo','Abril','Mayo','Junio','Julio','Agosto','Septiembre','Octubre','Noviembre','Diciembre');

        //crear series
        var arrayMontoBondes = [];
        for(var i=0; i<dataBondes.result.length;i++){
          arrayMontoBondes.push(parseFloat(dataBondes.result[i].montoBondes.replace(',','')));
        }
        var datMontoBondes = {
          name: 'Monto Bondes',
          data: arrayMontoBondes
        }

        var arrayMontoCetes = [];
        for(var i=0; i<dataBondes.result.length;i++){
          arrayMontoCetes.push(parseFloat(dataBondes.result[i].montoCetes.replace(',','')));
        }
        var datMontoCetes = {
          name: 'Monto Cetes',
          data: arrayMontoCetes
        }

        var arrayMontoBonddia = [];
        for(var i=0; i<dataBondes.result.length;i++){
          arrayMontoBonddia.push(parseFloat(dataBondes.result[i].montoBonddia.replace(',','')));
        }
        var datMontoBonddia = {
          name: 'Monto Bonddia',
          data: arrayMontoBonddia
        }

        var arrayCorteCupon = [];
        for(var i=0; i<dataBondes.result.length;i++){
          arrayCorteCupon.push(parseFloat(dataBondes.result[i].corteCupon.replace(',','')));
        }
        var datCorteCupon = {
          name: 'Corte cupon',
          data: arrayCorteCupon
        }
        dataSeries.push(datMontoBondes);
        dataSeries.push(datMontoCetes);
        dataSeries.push(datMontoBonddia);
        dataSeries.push(datCorteCupon);

      }else{
        cat = [];
        dataSeries = [];
        //crear categorias
        var  numCat = $('#plazo').val()/6
        for(var i=1; i<=numCat; i++){
          cat.push('Semestre ' + i);
        }

        //Crear series
        var arrayMontoBondes = [];
        for(var i=5; i<dataBondes.result.length;i+=6){
          arrayMontoBondes.push(parseFloat(dataBondes.result[i].montoBondes.replace(',','')));
        }
        var datMontoBondes = {
          name: 'Monto Bondes',
          data: arrayMontoBondes
        }

        var arrayMontoCetes = [];
        for(var i=5; i<dataBondes.result.length;i+=6){
          arrayMontoCetes.push(parseFloat(dataBondes.result[i].montoCetes.replace(',','')));
        }
        var datMontoCetes = {
          name: 'Monto Cetes',
          data: arrayMontoCetes
        }

        var arrayMontoBonddia = [];
        for(var i=5; i<dataBondes.result.length;i+=6){
          arrayMontoBonddia.push(parseFloat(dataBondes.result[i].montoBonddia.replace(',','')));
        }
        var datMontoBonddia = {
          name: 'Monto Bonddia',
          data: arrayMontoBonddia
        }

        var arrayCorteCupon = [];
        for(var i=5; i<dataBondes.result.length;i+=6){
          arrayCorteCupon.push(parseFloat(dataBondes.result[i].corteCupon.replace(',','')));
        }
        var datCorteCupon = {
          name: 'Corte cupon',
          data: arrayCorteCupon
        }
        dataSeries.push(datMontoBondes);
        dataSeries.push(datMontoCetes);
        dataSeries.push(datMontoBonddia);
        dataSeries.push(datCorteCupon);
      }

      Highcharts.chart('chart', {
        chart: {
            type: 'column'
        },
        title: {
            text: 'Inversión Bondes'
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
