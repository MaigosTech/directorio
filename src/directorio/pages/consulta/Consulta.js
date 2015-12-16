jQuery(document).ready(function($){                

                function initTablaP(){
                    var tableP = $('#tablaPersonas').DataTable( {
                        language: {
                            "sProcessing":     "Procesando...",
                            "sLengthMenu":     "Mostrar _MENU_ registros",
                            "sZeroRecords":    "No se encontraron resultados",
                            "sEmptyTable":     "Ningún dato disponible en esta tabla",
                            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
                            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
                            "sInfoPostFix":    "",
                            "sSearch":         "Buscar:",
                            "sUrl":            "",
                            "sInfoThousands":  ",",
                            "sLoadingRecords": "Cargando...",
                            "oPaginate": {
                                "sFirst":    "Primero",
                                "sLast":     "Último",
                                "sNext":     "Siguiente",
                                "sPrevious": "Anterior"
                            }
                        },
                        columnDefs : [
                            {
                                "targets": [0],
                                "visible": false
                            },
                            {
                              "targets": [1],
                              "visible": false  
                            },
                            {
                              "targets": [2],
                              "visible": false  
                            }
                        ]
                    } );
                    filtrosP(tableP);
                    return tableP;
                }

                function initTablaC(){
                    var tableC = $('#tablaCompanias').DataTable( {
                        language: {
                            "sProcessing":     "Procesando...",
                            "sLengthMenu":     "Mostrar _MENU_ registros",
                            "sZeroRecords":    "No se encontraron resultados",
                            "sEmptyTable":     "Ningún dato disponible en esta tabla",
                            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
                            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
                            "sInfoPostFix":    "",
                            "sSearch":         "Buscar:",
                            "sUrl":            "",
                            "sInfoThousands":  ",",
                            "sLoadingRecords": "Cargando...",
                            "oPaginate": {
                                "sFirst":    "Primero",
                                "sLast":     "Último",
                                "sNext":     "Siguiente",
                                "sPrevious": "Anterior"
                            }
                        }                        
                    } );
                    filtrosC(tableC);
                }

                function filtrosP(tableP){
                    $('#nombre').on( 'keyup', function () {
                        tableP
                            .columns( 0 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#paterno').on( 'keyup', function () {
                        tableP
                            .columns( 1 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#materno').on( 'keyup', function () {
                        tableP
                            .columns( 2 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#area').on( 'keyup', function () {
                        tableP
                            .columns( 6 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#cia').on( 'keyup', function () {
                        tableP
                            .columns( 5 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#puesto').on( 'keyup', function () {
                        tableP
                            .columns( 7 )
                            .search( this.value )
                            .draw();                    
                    });
                }

                function filtrosC(tableC){
                    $('#razonSocial').on( 'keyup', function () {
                        tableC
                            .columns( 0 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#nombreCorto').on( 'keyup', function () {
                        tableC
                            .columns( 1 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#ramo').on( 'keyup', function () {
                        tableC
                            .columns( 2 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#tipoMercado').on( 'keyup', function () {
                        tableC
                            .columns( 5 )
                            .search( this.value )
                            .draw();                    
                    });
                    $('#canalVentas').on( 'keyup', function () {
                        tableC
                            .columns( 4 )
                            .search( this.value )
                            .draw();
                    });
                    $('#capitalSocial').on( 'keyup', function () {
                        tableC
                            .columns( 5 )
                            .search( this.value )
                            .draw();
                    });
                }


                var tableP = initTablaP();
                initTablaC();
                $("#wrapperTablas").hide();
                $("#resultadoP").hide();
                $("#resultadoC").hide();

                $("#regresaP").click(function(){
                    console.log("Presionando regresaP");
                    $("#resultadoP").hide();
                    $("#wrapperTablas").hide();

                });
                $("#regresaC").click(function(){
                    console.log("Presionando regresaC");
                    $("#resultadoC").hide();
                    $("#wrapperTablas").hide();
                });

                $("#buscaP").click(function (){
                    console.log("Presionando buscaP");
                     $("#wrapperTablas").show();
                    $("#resultadoP").show();
                    $("#resultadoC").hide();
                });

                $("#buscaC").click(function (){
                    console.log("Presionando buscaC");
                     $("#wrapperTablas").show();
                    $("#resultadoC").show();
                    $("#resultadoP").hide();
                });

                function person(id) {
                    this.id = id;
                }
                $("#imprimeXlsP, #imprimePdfP").click(function(){                    
                    console.log("Generando arreglo de id's de personas");
                    var datos = [];                    
                    tableP.$('tr', {"filter":"applied"}).each(function(index){
                        datos.push($(this).find("td:eq(0)").text());
                    });
                    $("#tablaJson").val(datos.toString());
                    console.log($("#tablaJson").val());
                    /*tableP.columns(4).indexes().each(function(idx){
                            console.log(this.column(idx).data());
                    });
                    console.log();*/
                });
 });