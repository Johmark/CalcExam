package com.example.tucker.calcexam.activities;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tucker.calcexam.R;
import com.example.tucker.calcexam.database.Models.Historial;
import com.example.tucker.calcexam.subclases.ToDoViewHolder;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String en_pantalla;
    private String valor1 = "";
    private String valor2 = "";
    private String valorRes = "";
    private boolean isFirst = true;
    private boolean noPoint = true;
    private String op;
    public double num1, num2, numRes;
    private RecyclerView listaH;
    private static Context QuickContext;
    SoundPool sp;
    int sound_click_flick = 0;


    //todo Funciones que le permiten a la aplicacion calcular.

    public void mostrar (String imprimir)
    {
        //El metodod mostrar Imprime los datos ingrsesados por el usuario en el editText
        EditText Panel = findViewById(R.id.txtPanel);
        Panel.setText(imprimir);
    }

    public void clear()
    {
        //el metodo clear limpia el editText
        en_pantalla = "";
        mostrar(en_pantalla);
        isFirst = true;
        noPoint = true;
    }

    public void clean()
    {
        valor1 = "";
        valor2 = "";
    }

    public void recivirValor(String Valor_1, String Valor_2)
    {
        /*Este metodo Recive los valores en formato string y los combierte
        a double para respectiva operacion, asignando su valor a num1 y num2
        respectivamente.*/
       num1 = Double.parseDouble(Valor_1);
       num2 = Double.parseDouble(Valor_2);
    }
    

    public void MathError(String error)
    {
        if (error.contentEquals("divError"))
        {
            //Este metodo le indica al programa que hacer si se presenta un error de calculo
            Toast.makeText(getApplicationContext(), "Error de calculo, No es posible completar la operacion", Toast.LENGTH_LONG).show();
            valor2.isEmpty();
            recivirOperacion("div");
            isFirst = false;
            mostrar(valor1 + " ÷ ");
        }
        else if(error.contentEquals("pointError"))
        {
            //Este metodo le indica al programa que hacer si se presenta un error de calculo
            Toast.makeText(getApplicationContext(), "Error de calculo, No es colocar mas de un punto", Toast.LENGTH_LONG).show();
        }
    }

    public void recivirOperacion(String operacion)
    {
        // Este metodo indica al programa que operacion debe de hacer
        if (operacion.contentEquals("sum")){op = "+";}
        if (operacion.contentEquals("res")){op = "-";}
        if (operacion.contentEquals("mul")){op = "*";}
        if (operacion.contentEquals("div")){op = "/";}
        if (operacion.contentEquals("pow")){op = "^";}
    }

    public void Operar()
    {
        //el metodo Operar se encarga de realizar las operaciones solicitadas
        recivirValor(valor1, valor2);
        if (op.contentEquals("+")){numRes = num1 + num2;}
        else if (op.contentEquals("-")){numRes = num1 - num2;}
        else if (op.contentEquals("*")){numRes = num1 * num2;}
        else if (op.contentEquals("/")){if(num2 != 0){numRes = num1/num2;}else {MathError("divError");}}
        else if (op.contentEquals("^")){numRes = Math.pow(num1,num2);}
        else numRes = num1;

        valorRes = Double.toString(numRes);
    }


    //todo implementar Ans a opradores
    public void Ans (String LastOp)
    {
        /*Metodo Ads evalua si ya se hiso una operacion y en caso de que
        se alla efectuado dicha operacion envia el resultado al valor 1
        limpia valor 2, y prepara para la sigiente operacion*/
        if((valor2 != "" && isFirst == false) || numRes == Double.NaN)
        {

        }
    }

    //Todo save
    private void save()
    {
        Historial resgistro = new Historial();
        resgistro.operationHistory = valorRes;
        resgistro.save();
    }

    private void onUpdate ()
    {
        List<Historial> info = SQLite.select().from(Historial.class).queryList();
        listaH.setAdapter(new ToDoAdapter(info));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        QuickContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = new SoundPool(8, AudioManager.STREAM_MUSIC, 0);
        sound_click_flick = sp.load(this, R.raw.sound_click_flick, 1);

        //Declaracion del teclado numerico
        Button cero = findViewById(R.id.btn0);
        Button doblecero = findViewById(R.id.btn00);
        Button uno = findViewById(R.id.btn1);
        Button dos = findViewById(R.id.btn2);
        Button tres = findViewById(R.id.btn3);
        Button cuatro = findViewById(R.id.btn4);
        Button cinco = findViewById(R.id.btn5);
        Button seis = findViewById(R.id.btn6);
        Button siete = findViewById(R.id.btn7);
        Button ocho = findViewById(R.id.btn8);
        Button nueve = findViewById(R.id.btn9);
        Button point = findViewById(R.id.btnPoint);
        clear();

        cero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "0";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "0";
                } else {
                    valor2 = valor2 + "0";
                }

            }
        });
        doblecero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "00";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "00";
                } else {
                    valor2 = valor2 + "00";
                }
            }
        });
        uno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "1";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "1";
                } else {
                    valor2 = valor2 + "1";
                }
            }
        });
        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "2";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "2";
                } else {
                    valor2 = valor2 + "2";
                }
            }
        });
        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "3";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "3";
                } else {
                    valor2 = valor2 + "3";
                }
            }
        });
        cuatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "4";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "4";
                } else {
                    valor2 = valor2 + "4";
                }
            }
        });
        cinco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "5";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "5";
                } else {
                    valor2 = valor2 + "5";
                }
            }
        });
        seis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "6";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "6";
                } else {
                    valor2 = valor2 + "6";
                }
            }
        });
        siete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "7";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "7";
                } else {
                    valor2 = valor2 + "7";
                }
            }
        });
        ocho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "8";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "8";
                } else {
                    valor2 = valor2 + "8";
                }
            }
        });
        nueve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                en_pantalla = en_pantalla + "9";
                mostrar(en_pantalla);
                if (isFirst) {
                    valor1 = valor1 + "9";
                } else {
                    valor2 = valor2 + "9";
                }
            }
        });
        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                if(noPoint) {
                    en_pantalla = en_pantalla + ".";
                    mostrar(en_pantalla);
                    if (isFirst)
                    {
                        valor1 = valor1 + ".";
                    } else {
                        valor2 = valor2 + ".";
                    }
                    noPoint = false;
                } else {
                    MathError("pointError");
                }
            }
        });

        //Declaracion de los Operadores y controles
        Button add = findViewById(R.id.btnAdd);
        Button substact = findViewById(R.id.btnSub);
        Button multiply = findViewById(R.id.btnMul);
        Button divide = findViewById(R.id.btnDiv);
        Button power_of = findViewById(R.id.btnPow);
        Button is_equal = findViewById(R.id.btnRes);
        Button deleate = findViewById(R.id.btnDelete);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                //Ans("sum");
                recivirOperacion("sum");
                isFirst = false;
                noPoint = true;
                en_pantalla = en_pantalla + " + ";
                mostrar(en_pantalla);

            }
        });
        substact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                //Ans("res");
                recivirOperacion("res");
                isFirst = false;
                noPoint = true;
                en_pantalla = en_pantalla + " - ";
                mostrar(en_pantalla);
            }
        });
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                //Ans("mul");
                recivirOperacion("mul");
                isFirst = false;
                noPoint = true;
                en_pantalla = en_pantalla + " × ";
                mostrar(en_pantalla);
            }
        });
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                //Ans("div");
                recivirOperacion("div");
                isFirst = false;
                noPoint = true;
                en_pantalla = en_pantalla + " ÷ ";
                mostrar(en_pantalla);
            }
        });
        power_of.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                //Ans("pow");
                recivirOperacion("pow");
                isFirst = false;
                noPoint = true;
                en_pantalla = en_pantalla + " ^ ";
                mostrar(en_pantalla);
            }
        });
        deleate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                clear();
                clean();
                num1 = Double.NaN;
                num2 = Double.NaN;
                numRes = Double.NaN;
            }
        });

        is_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (sound_click_flick != 0){
                    sp.play(sound_click_flick, 1, 1 , 0, 0, 1);
                }
                clear();
                recivirOperacion(op);
                Operar();
                mostrar(valor1 + " " + op + " " + valor2 + " = " + valorRes);
                save();
                onUpdate();
                clean();
            }
        });

        listaH = findViewById(R.id.lista);
        listaH.setLayoutManager( new LinearLayoutManager(this));

        List<Historial> info = SQLite.select().from(Historial.class).queryList();
        listaH.setAdapter(new ToDoAdapter(info));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up buttom_calc, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            onUpdate();
        }
        return super.onOptionsItemSelected(item);
    }

    //Todo Adaptador
    public static class ToDoAdapter extends RecyclerView.Adapter<ToDoViewHolder> {
        private final List<Historial> listHistorial;
        private final LayoutInflater inflater;

        public ToDoAdapter(List<Historial> listHistorials) {
            this.inflater = LayoutInflater.from(QuickContext);
            this.listHistorial = listHistorials;
        }

        @Override
        public ToDoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.objeto, parent, false);
            return new ToDoViewHolder(view);
        }

        public void animateTo(List<Historial> models) {
            applyAndAnimateRemovals(models);
            applyAndAnimateAdditions(models);
            applyAndAnimateMovedItems(models);
        }

        private void applyAndAnimateRemovals(List<Historial> newModels) {
            for (int i = listHistorial.size() - 1; i >= 0; i--) {
                final Historial model = listHistorial.get(i);
                if (!newModels.contains(model)) {
                    removeItem(i);
                }
            }
        }

        private void applyAndAnimateAdditions(List<Historial> newModels) {
            for (int i = 0, count = newModels.size(); i < count; i++) {
                final Historial model = newModels.get(i);
                if (!listHistorial.contains(model)) {
                    addItem(i, model);
                }
            }
        }

        private void applyAndAnimateMovedItems(List<Historial> newModels) {
            for (int toPosition = newModels.size() - 1; toPosition >= 0; toPosition--) {
                final Historial model = newModels.get(toPosition);
                final int fromPosition = listHistorial.indexOf(model);
                if (fromPosition >= 0 && fromPosition != toPosition) {
                    moveItem(fromPosition, toPosition);
                }
            }
        }

        public Historial removeItem(int position) {
            final Historial model = listHistorial.remove(position);
            notifyItemRemoved(position);
            return model;
        }

        public void addItem(int position, Historial model) {
            listHistorial.add(position, model);
            notifyItemInserted(position);
        }

        public void moveItem(int fromPosition, int toPosition) {
            final Historial model = listHistorial.remove(fromPosition);
            listHistorial.add(toPosition, model);
            notifyItemMoved(fromPosition, toPosition);
        }



        @Override
        public void onBindViewHolder(final ToDoViewHolder holder, final int position) {
            final Historial current = listHistorial.get(position);
            holder.html.setHtml(ActividaString(current),
                    new HtmlResImageGetter(holder.html));


            holder.Delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current.delete();
                    removeItem(position);
                    notifyDataSetChanged();
                }
            });
        }


        private String ActividaString(Historial todo){

            String html = "<a><big><b>" + todo.operationHistory + "</b></big></a>";
            return html;
        }

        @Override
        public int getItemCount() {
            return listHistorial.size();
        }
    }
}
