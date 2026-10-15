package co.edu.poli.sw2.vista;

import java.time.LocalDate;
import co.edu.poli.sw2.infraestructura.persistencia.MySqlInmuebleRepository;
import java.util.List;
import java.util.function.Function;

import co.edu.poli.sw2.aplicacion.servicio.*;
import co.edu.poli.sw2.aplicacion.puerto.salida.InmuebleRepository;
import co.edu.poli.sw2.dominio.modelo.*;
import co.edu.poli.sw2.infraestructura.ui.InmuebleController;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicacion de gestion de inmuebles.
 * Implementa la interfaz grafica utilizando JavaFX.
 *
 * Permite crear, consultar, actualizar y eliminar inmuebles.
 */
public class App extends Application {

    private InmuebleController controller;

    // Campos del formulario
    private final TextField txtChip = new TextField();
    private final DatePicker fechaCompra = new DatePicker();
    private final TextField txtDireccion = new TextField();

    private final ComboBox<String> cbEstado = new ComboBox<>();
    private final ComboBox<Propietario> cbPropietario = new ComboBox<>();
    private final ComboBox<String> cbTipo = new ComboBox<>();

    private final TextField txtPisos = new TextField();
    private final CheckBox chkSotano = new CheckBox("Tiene sotano");
    private final TextField txtArea = new TextField();
    private final TextField txtUso = new TextField();
    private final TextField txtBuscar = new TextField();

    // Tabla para mostrar los inmuebles
    private final TableView<Inmueble> tabla = new TableView<>();

    /**
     * Inicia la aplicacion y construye la ventana principal.
     *
     * @param stage escenario principal de JavaFX
     */
    @Override
    public void start(Stage stage) {

        // Repositorio temporal en memoria
        InmuebleRepository repo = new MySqlInmuebleRepository();

        // Inyeccion de dependencias de los servicios
        controller = new InmuebleController(
            new CrearInmuebleServicio(repo),
            new LeerUnoInmuebleServicio(repo),
            new LeerTodosInmuebleServicio(repo),
            new ModificarInmuebleServicio(repo),
            new EliminarInmuebleServicio(repo)
        );

        cargarPropietarios();
        configurarTabla();

        Label titulo = new Label("GESTION DE INMUEBLES");
        titulo.setStyle(
            "-fx-font-size: 24px; " +
            "-fx-font-weight: bold; " +
            "-fx-text-fill: #183153;"
        );

        Label subtitulo = new Label(
            "Sistema de administracion de casas y apartamentos"
        );

        cbEstado.getItems().addAll(
            "Disponible",
            "Ocupado",
            "En mantenimiento"
        );
        cbEstado.setValue("Disponible");

        cbTipo.getItems().addAll(
            "Apartamento",
            "Casa"
        );
        cbTipo.setValue("Apartamento");

        cbTipo.setOnAction(e -> actualizarCampos());
        chkSotano.setOnAction(e -> actualizarCampos());

        actualizarCampos();

        // Formulario
        GridPane formulario = new GridPane();
        formulario.setHgap(12);
        formulario.setVgap(12);
        formulario.setPadding(new Insets(15));

        int fila = 0;

        formulario.add(new Label("CHIP:"), 0, fila);
        formulario.add(txtChip, 1, fila++);

        formulario.add(new Label("Fecha de compra:"), 0, fila);
        formulario.add(fechaCompra, 1, fila++);

        formulario.add(new Label("Direccion:"), 0, fila);
        formulario.add(txtDireccion, 1, fila++);

        formulario.add(new Label("Estado:"), 0, fila);
        formulario.add(cbEstado, 1, fila++);

        formulario.add(new Label("Propietario:"), 0, fila);
        formulario.add(cbPropietario, 1, fila++);

        formulario.add(new Label("Tipo de inmueble:"), 0, fila);
        formulario.add(cbTipo, 1, fila++);

        formulario.add(
            new Label("Numero / cantidad de pisos:"), 0, fila
        );
        formulario.add(txtPisos, 1, fila++);

        formulario.add(chkSotano, 1, fila++);

        formulario.add(new Label("Area sotano (m2):"), 0, fila);
        formulario.add(txtArea, 1, fila++);

        formulario.add(new Label("Tipo de uso sotano:"), 0, fila);
        formulario.add(txtUso, 1, fila++);

        txtChip.setPrefWidth(270);
        txtDireccion.setPrefWidth(270);

        // Botones CRUD
        Button btnCrear = new Button("Crear inmueble");
        Button btnConsultar = new Button("Consultar CHIP");
        Button btnActualizar = new Button("Actualizar");
        Button btnEliminar = new Button("Eliminar");
        Button btnLimpiar = new Button("Limpiar");

        btnCrear.setStyle(
            "-fx-background-color: #2e7d32; " +
            "-fx-text-fill: white;"
        );

        btnActualizar.setStyle(
            "-fx-background-color: #1565c0; " +
            "-fx-text-fill: white;"
        );

        btnEliminar.setStyle(
            "-fx-background-color: #c62828; " +
            "-fx-text-fill: white;"
        );

        // CREAR
        btnCrear.setOnAction(e -> ejecutar(() -> {

            Inmueble inmueble = crearDesdeFormulario();

            controller.crear(inmueble);

            refrescarTabla();
            limpiar();

            mensaje("Inmueble creado correctamente.");
        }));

        // CONSULTAR
        btnConsultar.setOnAction(e -> ejecutar(() -> {

            String chip = txtBuscar.getText().trim();

            Inmueble inmueble = controller.consultar(chip)
                .orElseThrow(() -> new IllegalArgumentException(
                    "No se encontro el inmueble con CHIP: " + chip
                ));

            tabla.getSelectionModel().select(inmueble);
            tabla.scrollTo(inmueble);
            cargarFormulario(inmueble);

            mensaje("Inmueble encontrado correctamente.");
        }));

        // ACTUALIZAR
        btnActualizar.setOnAction(e -> ejecutar(() -> {

            Inmueble seleccionado =
                tabla.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                throw new IllegalArgumentException(
                    "Debes seleccionar un inmueble de la tabla."
                );
            }

            if (!seleccionado.getChip().equals(
                    txtChip.getText().trim())) {

                throw new IllegalArgumentException(
                    "No se permite modificar el CHIP."
                );
            }

            Inmueble actualizado = crearDesdeFormulario();

            controller.modificar(actualizado);

            refrescarTabla();
            limpiar();

            mensaje("Inmueble actualizado correctamente.");
        }));

        // ELIMINAR
        btnEliminar.setOnAction(e -> ejecutar(() -> {

            Inmueble seleccionado =
                tabla.getSelectionModel().getSelectedItem();

            if (seleccionado == null) {
                throw new IllegalArgumentException(
                    "Selecciona un inmueble para eliminar."
                );
            }

            Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Deseas eliminar el inmueble "
                    + seleccionado.getChip() + "?",
                ButtonType.YES,
                ButtonType.NO
            );

            if (confirmacion.showAndWait()
                    .orElse(ButtonType.NO) != ButtonType.YES) {
                return;
            }

            controller.eliminar(seleccionado.getChip());

            refrescarTabla();
            limpiar();

            mensaje("Inmueble eliminado correctamente.");
        }));

        // LIMPIAR
        btnLimpiar.setOnAction(e -> limpiar());

        HBox botones = new HBox(
            12,
            btnCrear,
            btnActualizar,
            btnEliminar,
            btnLimpiar
        );

        HBox busqueda = new HBox(
            10,
            new Label("Buscar por CHIP:"),
            txtBuscar,
            btnConsultar
        );

        Label tituloTabla = new Label("INMUEBLES REGISTRADOS");
        tituloTabla.setStyle(
            "-fx-font-size: 17px; -fx-font-weight: bold;"
        );

        VBox contenido = new VBox(
            15,
            titulo,
            subtitulo,
            formulario,
            botones,
            busqueda,
            tituloTabla,
            tabla
        );

        contenido.setPadding(new Insets(20));

        ScrollPane scroll = new ScrollPane(contenido);
        scroll.setFitToWidth(true);

        Scene escena = new Scene(scroll, 950, 650);

        stage.setScene(escena);
        stage.setTitle("Gestion de Inmuebles - JavaFX");
        stage.setResizable(true);
        stage.centerOnScreen();
        stage.show();

        refrescarTabla();
    }

    /**
     * Carga los propietarios predeterminados.
     * Un propietario puede tener varios inmuebles.
     */
    private void cargarPropietarios() {

        cbPropietario.getItems().addAll(
            new Propietario(
                "1001", "Ana Gomez", "ana@correo.com"
            ),
            new Propietario(
                "1002", "Luis Perez", "luis@correo.com"
            ),
            new Propietario(
                "1003", "Maria Lopez", "maria@correo.com"
            )
        );

        cbPropietario.getSelectionModel().selectFirst();
    }

    /**
     * Crea un objeto Casa o Apartamento
     * con los datos ingresados en el formulario.
     *
     * @return inmueble construido
     */
    private Inmueble crearDesdeFormulario() {

        String chip = txtChip.getText().trim();
        LocalDate fecha = fechaCompra.getValue();
        String direccion = txtDireccion.getText().trim();
        String estado = cbEstado.getValue();
        Propietario propietario = cbPropietario.getValue();

        int pisos;

        try {
            pisos = Integer.parseInt(
                txtPisos.getText().trim()
            );
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(
                "Debes ingresar un numero de pisos valido."
            );
        }

        // Crear apartamento
        if ("Apartamento".equals(cbTipo.getValue())) {

            return new Apartamento(
                chip,
                fecha,
                direccion,
                estado,
                propietario,
                pisos
            );
        }

        // Crear casa
        Sotano sotano = null;

        if (chkSotano.isSelected()) {

            double area;

            try {
                area = Double.parseDouble(
                    txtArea.getText().trim().replace(",", ".")
                );
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException(
                    "El area del sotano debe ser numerica."
                );
            }

            sotano = new Sotano(
                area,
                txtUso.getText().trim()
            );
        }

        return new Casa(
            chip,
            fecha,
            direccion,
            estado,
            propietario,
            pisos,
            sotano
        );
    }

    /**
     * Configura todas las columnas de la tabla.
     */
    private void configurarTabla() {

        TableColumn<Inmueble, String> chip = columna(
            "CHIP",
            i -> i.getChip()
        );

        TableColumn<Inmueble, String> tipo = columna(
            "Tipo",
            i -> i.getTipo()
        );

        TableColumn<Inmueble, String> direccion = columna(
            "Direccion",
            i -> i.getDireccion()
        );

        TableColumn<Inmueble, String> estado = columna(
            "Estado",
            i -> i.getEstado()
        );

        TableColumn<Inmueble, String> fecha = columna(
            "Fecha compra",
            i -> i.getFechaCompra().toString()
        );

        TableColumn<Inmueble, String> propietario = columna(
            "Propietario",
            i -> i.getPropietario().getNombre()
        );

        // Numero de piso o cantidad de pisos
        TableColumn<Inmueble, String> pisos = columna(
            "Pisos",
            i -> {
                if (i instanceof Apartamento a) {
                    return String.valueOf(a.getNumeroPiso());
                }

                if (i instanceof Casa c) {
                    return String.valueOf(c.getCantidadPisos());
                }

                return "-";
            }
        );

        // Indica si tiene sotano
        TableColumn<Inmueble, String> sotano = columna(
            "Sotano",
            i -> {
                if (i instanceof Casa c) {
                    return c.getSotano() != null ? "Si" : "No";
                }

                return "No aplica";
            }
        );

        // Area del sotano
        TableColumn<Inmueble, String> areaSotano = columna(
            "Area sotano (m2)",
            i -> {
                if (i instanceof Casa c
                        && c.getSotano() != null) {

                    return String.valueOf(
                        c.getSotano().getAreaM2()
                    );
                }

                return "-";
            }
        );

        // Tipo de uso del sotano
        TableColumn<Inmueble, String> usoSotano = columna(
            "Uso sotano",
            i -> {
                if (i instanceof Casa c
                        && c.getSotano() != null) {

                    return c.getSotano().getTipoUso();
                }

                return "-";
            }
        );

        tabla.getColumns().addAll(chip,tipo,direccion,estado,fecha, propietario,pisos,sotano,areaSotano,usoSotano);

        tabla.setColumnResizePolicy(
            TableView.UNCONSTRAINED_RESIZE_POLICY
        );

        tabla.setPrefHeight(320);
        tabla.setPrefWidth(1200);

        // Cargar los datos al seleccionar un inmueble
        tabla.getSelectionModel()
            .selectedItemProperty()
            .addListener((obs, anterior, actual) -> {

                if (actual != null) {
                    cargarFormulario(actual);
                }
            });
    }

    /**
     * Metodo auxiliar para crear columnas de texto.
     *
     * @param titulo nombre de la columna
     * @param obtener funcion que obtiene el valor
     * @return columna configurada
     */
    private TableColumn<Inmueble, String> columna(
            String titulo,
            Function<Inmueble, String> obtener) {

        TableColumn<Inmueble, String> col =
            new TableColumn<>(titulo);

        col.setCellValueFactory(datos ->
            new ReadOnlyStringWrapper(
                obtener.apply(datos.getValue())
            )
        );

        col.setPrefWidth(130);

        return col;
    }

    /**
     * Carga los datos del inmueble seleccionado
     * en el formulario para su modificacion.
     *
     * @param i inmueble seleccionado
     */
    private void cargarFormulario(Inmueble i) {

        txtChip.setText(i.getChip());
        fechaCompra.setValue(i.getFechaCompra());
        txtDireccion.setText(i.getDireccion());
        cbEstado.setValue(i.getEstado());

        cbPropietario.getItems()
            .stream()
            .filter(p ->
                p.getIdentificacion().equals(
                    i.getPropietario().getIdentificacion()
                )
            )
            .findFirst()
            .ifPresent(cbPropietario::setValue);

        cbTipo.setValue(i.getTipo());

        if (i instanceof Apartamento a) {

            txtPisos.setText(
                String.valueOf(a.getNumeroPiso())
            );

            chkSotano.setSelected(false);
            txtArea.clear();
            txtUso.clear();

        } else if (i instanceof Casa c) {

            txtPisos.setText(
                String.valueOf(c.getCantidadPisos())
            );

            chkSotano.setSelected(c.getSotano() != null);

            if (c.getSotano() != null) {

                txtArea.setText(
                    String.valueOf(
                        c.getSotano().getAreaM2()
                    )
                );

                txtUso.setText(
                    c.getSotano().getTipoUso()
                );

            } else {
                txtArea.clear();
                txtUso.clear();
            }
        }

        actualizarCampos();
    }

    /**
     * Activa o desactiva los campos de sotano
     * segun el tipo de inmueble seleccionado.
     */
    private void actualizarCampos() {

        boolean esCasa = "Casa".equals(cbTipo.getValue());

        chkSotano.setDisable(!esCasa);

        if (!esCasa) {
            chkSotano.setSelected(false);
        }

        boolean tieneSotano =
            esCasa && chkSotano.isSelected();

        txtArea.setDisable(!tieneSotano);
        txtUso.setDisable(!tieneSotano);
    }

    /**
     * Actualiza los datos mostrados en la tabla.
     */
    private void refrescarTabla() {

        List<Inmueble> inmuebles = controller.listar();

        tabla.setItems(
            FXCollections.observableArrayList(inmuebles)
        );

        tabla.refresh();
    }

    /**
     * Limpia los campos del formulario
     * sin eliminar los registros.
     */
    private void limpiar() {

        tabla.getSelectionModel().clearSelection();

        txtChip.clear();
        fechaCompra.setValue(null);
        txtDireccion.clear();

        cbEstado.setValue("Disponible");
        cbPropietario.getSelectionModel().selectFirst();
        cbTipo.setValue("Apartamento");

        txtPisos.clear();

        chkSotano.setSelected(false);
        txtArea.clear();
        txtUso.clear();
        txtBuscar.clear();

        actualizarCampos();
    }

    /**
     * Ejecuta las operaciones y muestra
     * mensajes cuando ocurre un error.
     *
     * @param accion operacion a ejecutar
     */
    private void ejecutar(Runnable accion) {

        try {
            accion.run();

        } catch (Exception ex) {

            Alert alerta = new Alert(
                Alert.AlertType.ERROR,
                ex.getMessage() == null
                    ? "Ocurrio un error."
                    : ex.getMessage()
            );

            alerta.setHeaderText(
                "No se pudo realizar la operacion"
            );

            alerta.showAndWait();
        }
    }

    /**
     * Muestra mensajes informativos.
     *
     * @param texto mensaje que se mostrara
     */
    private void mensaje(String texto) {

        Alert alerta = new Alert(
            Alert.AlertType.INFORMATION,
            texto
        );

        alerta.setHeaderText(null);
        alerta.showAndWait();
    }

    /**
     * Metodo principal de JavaFX.
     *
     * @param args argumentos de ejecucion
     */
    public static void main(String[] args) {
        launch(args);
    }
}