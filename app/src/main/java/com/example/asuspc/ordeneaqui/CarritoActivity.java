package com.example.asuspc.ordeneaqui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asuspc.ordeneaqui.adapters.ItemCartAdapter;
import com.example.asuspc.ordeneaqui.models.Item;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;


public class CarritoActivity extends AppCompatActivity {

    //Arreglo de productos
    private ArrayList<Item> listaProductos;
    private ArrayList<String> listaProductosString;
    private Item nuevoProducto;
    private static final int PRODUCTONUEVO = -1;
    private static final int PUNTO_DECIMAL = 2;

    private double monto = 0;

    //Variables para la lectura de los NFC tags
    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";

    private TextView montoDeCompra;
    private ListView listaCarrito;

    private NfcAdapter mNfcAdapter;
    private ItemCartAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaCarrito = (ListView) findViewById(R.id.cart_items_list);
        montoDeCompra = (TextView) findViewById(R.id.monto_money);

        listaProductos = new ArrayList<Item>();
        listaProductosString = new ArrayList<String>();
        nuevoProducto = null;

        //Lectura de NFC tags
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mNfcAdapter == null) {
            // Stop here, we definitely need NFC
            Toast.makeText(this, "Este dispositivo no soporta la tecnología NFC.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        if (!mNfcAdapter.isEnabled()) {
            Toast.makeText(getApplicationContext(), "NFC está deshabilitado, habilitarlo en configuración del dispositivo.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Seleccione sus productos", Toast.LENGTH_SHORT).show();
        }

        //Para leer los datos del producto del NFC Tag
        handleIntent(getIntent());

        arrayAdapter = new ItemCartAdapter(CarritoActivity.this, listaProductos);

        //arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaProductosString);
        listaCarrito.setAdapter(arrayAdapter);
        registerForContextMenu(listaCarrito);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (monto != 0.0) {
                    //Ir al intent facturacion
                    Toast.makeText(getApplicationContext(), "Monto: RD$ " + monto, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "No hay monto que facturar", Toast.LENGTH_LONG).show();
                }
               /* if (monto != 0.0) {
                    //Ir a facturacion
                    Usuario usuario = RegistroHelper.getInstancia(CarritoActivity.this).getUsuarioActual();
                    if(usuario != null){
                        Intent intent = new Intent(CarritoActivity.this, ProcesoPagoActivity.class);
                        intent.putExtra(ProcesoPagoActivity.PAYMENT_DATA_EXTRA, (Double)(Math.round(monto * 100.0) / 100.0));
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(CarritoActivity.this, RegistroActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No hay monto que facturar", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }

    protected int buscarCoincidencia(int codigoProducto) {
        //Recorriendo arraylist para verificar si el producto ya esta en el carrito
        for (int i = 0; i < listaProductos.size(); i++) {

            if (listaProductos.get(i).getId_item() == codigoProducto) {
                return i;
            }
        }
        return PRODUCTONUEVO;
    }

    protected void agregarProducto(Item producto) {
        int productoIndice = buscarCoincidencia(producto.getId_item());
        if (productoIndice != PRODUCTONUEVO) {

            //Aumentar 1 unidad en la cantidad

            int cantidadEnCarrito = listaProductos.get(productoIndice).getQuantity();

            producto.setQuantity(cantidadEnCarrito + 1);
            listaProductos.get(productoIndice).setQuantity(cantidadEnCarrito + 1);
            listaProductosString.set(productoIndice, producto.getName() + " - " + producto.getDescription() + " - " + producto.getPrice() + " - " + producto.getQuantity());

            monto = monto + producto.getPrice();
            Toast.makeText(getApplicationContext(), "Agregó otro/a " + nuevoProducto.getName() + " al carrito.", Toast.LENGTH_LONG).show();

        } else {
            listaProductos.add(producto);
            listaProductosString.add(producto.getName() + " - " + producto.getDescription() + " - " + producto.getPrice() + " - " + producto.getQuantity());

            monto = monto + producto.getPrice();

            Toast.makeText(getApplicationContext(), "Agregó un nuevo producto al carrito.", Toast.LENGTH_LONG).show();
        }

        arrayAdapter.notifyDataSetChanged();

        montoDeCompra.setText("" + (Double)(Math.round(monto * 100.0) / 100.0));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Seleccionar opción");
        menu.add(0, 1, 0, "Modificar cantidad");
        menu.add(0, 2, 1, "Sacar uno del carrito");
        menu.add(0, 3, 2, "Eliminar del carrito");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        final AdapterView.AdapterContextMenuInfo productoSeleccionado = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if (item.getTitle().equals("Modificar cantidad")) {
            for (int i = 0; i < listaProductos.size(); i++) {

                if (i == productoSeleccionado.position) {
                    final int indice = i;

                    //Edit Dialog para modificar la cantidad de un producto en el carrito manualmente
                    AlertDialog.Builder alert = new AlertDialog.Builder(this);
                    alert.setTitle(listaProductos.get(i).getName());
                    alert.setMessage("Cantidad en el carrito");

                    // Crear EditText para introducir nueva cantidad
                    final EditText editCantidad = new EditText(this);
                    editCantidad.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editCantidad.setRawInputType(Configuration.KEYBOARD_12KEY);
                    editCantidad.setText("" + listaProductos.get(i).getQuantity() + "");
                    alert.setView(editCantidad);

                    // Boton "OK" para actualizar a nueva cantidad del producto en el carrito
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            int nuevaCantidad = Integer.parseInt(editCantidad.getText().toString());

                            monto = monto - (listaProductos.get(indice).getPrice() * listaProductos.get(indice).getQuantity());

                            listaProductos.get(indice).setQuantity(nuevaCantidad);
                            listaProductosString.set(indice, listaProductos.get(indice).getName() + " - " + listaProductos.get(indice).getDescription() + " - " + listaProductos.get(indice).getPrice() + " - " + listaProductos.get(indice).getQuantity());

                            monto = monto + (listaProductos.get(indice).getPrice() * listaProductos.get(indice).getQuantity());

                            //Actualizando lista y monto
                            arrayAdapter.notifyDataSetChanged();
                            montoDeCompra.setText("" + (Double)(Math.round(monto * 100.0) / 100.0));

                            // Notificar al usuario que se ha modificado
                            Toast.makeText(getApplicationContext(), "Ahora tiene " + nuevaCantidad + " de " + listaProductos.get(indice).getName() + " en el carrito", Toast.LENGTH_LONG).show();
                        }
                    });

                    // Boton "Cancelar"
                    alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                        }
                    });

                    alert.show();

                }
            }

        } else if (item.getTitle().equals("Sacar uno del carrito")) {

            //Sacar una unidad del producto del carrito
            int cantidadEnCarrito;

            for (int i = 0; i < listaProductos.size(); i++) {

                if (i == productoSeleccionado.position) {

                    cantidadEnCarrito = listaProductos.get(i).getQuantity();

                    if (cantidadEnCarrito == 1) {
                        monto = monto - listaProductos.get(i).getPrice();

                        listaProductos.remove(i);
                        listaProductosString.remove(i);

                    } else {
                        listaProductos.get(i).setQuantity(cantidadEnCarrito - 1);
                        listaProductosString.set(i, listaProductos.get(i).getName() + " - " + listaProductos.get(i).getDescription() + " - " + listaProductos.get(i).getPrice() + " - " + listaProductos.get(i).getQuantity());

                        monto = monto - listaProductos.get(i).getPrice();
                    }
                }
            }
        } else if (item.getTitle().equals("Eliminar del carrito")) {

            //Eliminar el producto del carrito, es decir, sacar todas las unidades
            for (int i = 0; i < listaProductos.size(); i++) {

                if (i == productoSeleccionado.position) {
                    monto = monto - (listaProductos.get(i).getPrice() * listaProductos.get(i).getQuantity());

                    listaProductos.remove(i);
                    listaProductosString.remove(i);
                }
            }
        } else {
            return false;
        }

        //Actualizando lista y monto
        arrayAdapter.notifyDataSetChanged();
        montoDeCompra.setText("" + (Double)(Math.round(monto * 100.0) / 100.0));

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* It's important, that the activity is in the foreground (resumed). Otherwise
         * an IllegalStateException is thrown.*/
        setupForegroundDispatch(this, mNfcAdapter);
    }

    @Override
    protected void onPause() {
        //Call this before onPause, otherwise an IllegalArgumentException is thrown as well.
        stopForegroundDispatch(this, mNfcAdapter);

        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /**
         * This method gets called, when a new Intent gets associated with the current activity instance.
         * Instead of creating a new activity, onNewIntent will be called.
         * In our case this method gets called, when the user attaches a Tag to the device.
         */
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            if (MIME_TEXT_PLAIN.equals(type)) {

                Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                new NdefReaderTask().execute(tag);

            } else {
                Log.d(TAG, "Wrong mime type: " + type);
            }
        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            // In case we would still use the Tech Discovered Intent
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            String[] techList = tag.getTechList();
            String searchedTech = Ndef.class.getName();

            for (String tech : techList) {
                if (searchedTech.equals(tech)) {
                    new NdefReaderTask().execute(tag);
                    break;
                }
            }
        }
    }

    /**
     * @param activity The corresponding {@link Activity} requesting the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }

        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    /**
     * @param activity The corresponding {@link Activity} requesting to stop the foreground dispatch.
     * @param adapter  The {@link NfcAdapter} used for the foreground dispatch.
     */
    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }


    private class NdefReaderTask extends AsyncTask<Tag, Void, String> {

        @Override
        protected String doInBackground(Tag... params) {
            Tag tag = params[0];

            Ndef ndef = Ndef.get(tag);
            if (ndef == null) {
                // Este tag no soporta NDEF.
                return null;
            }

            NdefMessage ndefMessage = ndef.getCachedNdefMessage();

            NdefRecord[] records = ndefMessage.getRecords();
            for (NdefRecord ndefRecord : records) {
                if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                    try {
                        return readText(ndefRecord);
                    } catch (UnsupportedEncodingException e) {
                        Log.e(TAG, "Unsupported Encoding", e);
                    }
                }
            }
            return null;
        }

        private String readText(NdefRecord record) throws UnsupportedEncodingException {

            byte[] payload = record.getPayload();

            // Get the Text Encoding
            String textEncoding;
            if ((payload[0] & 128) == 0) {
                textEncoding = "UTF-8";
            } else {
                textEncoding = "UTF-16";
            }

            // Get the Language Code
            int languageCodeLength = payload[0] & 0063;

            // String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            // e.g. "en"

            // Get the Text
            return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        }

        @Override
        protected void onPostExecute(String resultadoLectura) {
            if (resultadoLectura != null) {

                JSONObject itemTagNFC = null;
                try {
                    itemTagNFC = new JSONObject(resultadoLectura);
                    nuevoProducto = new Item(Integer.parseInt(itemTagNFC.getString("id")),
                            Integer.parseInt(itemTagNFC.getString("from")),
                            itemTagNFC.getString("nombre"), itemTagNFC.getString("descripcion"),
                            Double.parseDouble(itemTagNFC.getString("precio")), 1,
                            Integer.parseInt(itemTagNFC.getString("thumb")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                agregarProducto(nuevoProducto);
            }
        }
    }
}
