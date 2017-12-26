

package com.sastroman.angga.androidlearn.view;

/**
 * Created by Angga N P on 12/13/2017.
 */


        import android.app.Activity;
        import android.graphics.drawable.ColorDrawable;
        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.support.design.widget.TextInputLayout;
        import android.support.v4.app.Fragment;
        import android.support.v7.app.AppCompatActivity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.FrameLayout;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.sastroman.angga.androidlearn.R;
        import com.sastroman.angga.androidlearn.helper.SQLiteHandler;
        import com.transitionseverywhere.Fade;
        import com.transitionseverywhere.TransitionManager;
        import com.transitionseverywhere.TransitionSet;
        import com.transitionseverywhere.extra.Scale;

        import java.util.HashMap;


public class FragmentAccount extends Fragment {

    public FragmentAccount() {
        // Required empty public constructor
    }

    private LinearLayout LLD, LLCP, LLCD;
    private static String TAG = MainActivity.class.getSimpleName();
    private SQLiteHandler db;
    private ImageView iv, ivf;
    private TextView name, email, phone, address, tvcp, tvcd;
    private Button btcp, btcd, btccp, btccd, btcancel, btcancel2;
    private android.support.v7.widget.Toolbar toolbar;
    private EditText pwold, pwnew, pwconfirm, cname, caddress, cphone;
    private HashMap<String, String> user;
    private TransitionSet inset, outset;
    private FloatingActionButton fab;
    private FrameLayout FLF;
    private Boolean p, a, s, pa, sw = false;
    private TextInputLayout input_layout_cname,input_layout_caddress,
            input_layout_cphone,input_layout_pwold,input_layout_pwnew, input_layout_pwconfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account, container, false);
        setHasOptionsMenu(true);

        toolbar = getActivity().findViewById(R.id.toolbar);
        tvcp = (TextView)rootView.findViewById(R.id.tvcp);
        tvcd = (TextView)rootView.findViewById(R.id.tvcd);
        LLD = (LinearLayout)rootView.findViewById(R.id.LLD);
        LLCP = (LinearLayout)rootView.findViewById(R.id.LLCP);
        LLCD = (LinearLayout)rootView.findViewById(R.id.LLCD);
        FLF = (FrameLayout)rootView.findViewById(R.id.FLF);
        iv = (ImageView)rootView.findViewById(R.id.iv);
        ivf = (ImageView)rootView.findViewById(R.id.ivf);
        name = (TextView)rootView.findViewById(R.id.name);
        email = (TextView)rootView.findViewById(R.id.email);
        phone = (TextView)rootView.findViewById(R.id.phone);
        address = (TextView)rootView.findViewById(R.id.address);
        btcp = (Button)rootView.findViewById(R.id.btcp);
        btcd = (Button)rootView.findViewById(R.id.btcd);
        btccp = (Button)rootView.findViewById(R.id.btccp);
        btccd = (Button)rootView.findViewById(R.id.btccd);
        btcancel = (Button)rootView.findViewById(R.id.btcancel);
        btcancel2 = (Button)rootView.findViewById(R.id.btcancel2);
        pwold = (EditText)rootView.findViewById(R.id.pwold);
        pwnew = (EditText)rootView.findViewById(R.id.pwnew);
        pwconfirm = (EditText)rootView.findViewById(R.id.pwconfirm);
        cname = (EditText)rootView.findViewById(R.id.cname);
        caddress = (EditText)rootView.findViewById(R.id.caddress);
        cphone = (EditText)rootView.findViewById(R.id.cphone);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab);
        input_layout_cname = (TextInputLayout)rootView.findViewById(R.id.input_layout_cname);
        input_layout_caddress = (TextInputLayout)rootView.findViewById(R.id.input_layout_cadress);
        input_layout_cphone = (TextInputLayout)rootView.findViewById(R.id.input_layout_cphone);
        input_layout_pwold = (TextInputLayout)rootView.findViewById(R.id.input_layout_pwold);
        input_layout_pwnew = (TextInputLayout)rootView.findViewById(R.id.input_layout_pwnew);
        input_layout_pwconfirm = (TextInputLayout)rootView.findViewById(R.id.input_layout_pwconfirm);

        //Change Color View
        setView();

        db = new SQLiteHandler(getActivity().getApplicationContext());

        // Fetching user details from sqlite
        user = db.getUserDetails();

        // Displaying the user details on the screen
        name.setText(user.get("name"));
        email.setText(user.get("email"));
        phone.setText(user.get("phone"));
        address.setText(user.get("address"));

        btcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Displaying the user details on the screen
                cname.setText(user.get("name"));
                cphone.setText(user.get("phone"));
                caddress.setText(user.get("address"));

                TransitionManager.beginDelayedTransition(LLCD, inset);
                TransitionManager.beginDelayedTransition(LLD, outset);

                LLD.setVisibility(View.GONE);
                LLCD.setVisibility(View.VISIBLE);
            }
        });

        btcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwold.setText("");
                pwnew.setText("");
                pwconfirm.setText("");

                TransitionManager.beginDelayedTransition(LLCP, inset);
                TransitionManager.beginDelayedTransition(LLD, outset);

                LLD.setVisibility(View.GONE);
                LLCP.setVisibility(View.VISIBLE);
            }
        });

        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(LLD, inset);
                TransitionManager.beginDelayedTransition(LLCP, outset);

                LLCP.setVisibility(View.GONE);
                LLD.setVisibility(View.VISIBLE);
            }
        });

        btcancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(LLD, inset);
                TransitionManager.beginDelayedTransition(LLCD, outset);

                LLCD.setVisibility(View.GONE);
                LLD.setVisibility(View.VISIBLE);
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivf.setImageDrawable(iv.getDrawable());
                TransitionManager.beginDelayedTransition(FLF, inset);

                LLD.setVisibility(View.GONE);
                FLF.setVisibility(View.VISIBLE);
            }
        });

        ivf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivf.setImageDrawable(iv.getDrawable());
                TransitionManager.beginDelayedTransition(LLD, inset);

                LLD.setVisibility(View.VISIBLE);
                FLF.setVisibility(View.GONE);
            }
        });

        btccp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pa && sw){

                }else {
                    Toast.makeText(getContext(), "Please enter or fix your details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btccd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (p && a && s){

                }else{
                    Toast.makeText(getContext(), "Please enter or fix your details!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!cname.getText().toString().equals("")) {
                        if (cname.getText().toString().length() < 6) {
                            p = false;
                            input_layout_cname.setError(getString(R.string.hint_err_name));
                        } else {
                            p = true;
                            input_layout_cname.setErrorEnabled(false);
                        }
                    }
                }
            }
        });

        caddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!caddress.getText().toString().equals("")) {
                        if (!caddress.getText().toString().equals("")) {
                            if (caddress.getText().toString().length() < 5) {
                                a = false;
                                input_layout_caddress.setError(getString(R.string.hint_err_address));
                            } else {
                                a = true;
                                input_layout_caddress.setErrorEnabled(false);
                            }
                        }

                    }
                }
            }
        });

        cphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (!cphone.getText().toString().equals("")){
                        if(cphone.getText().toString().length() < 11){
                            s = false;
                            input_layout_cphone.setError(getString(R.string.hint_err_phone));
                        } else {
                            s = true;
                            input_layout_cphone.setErrorEnabled(false);
                        }
                    }
                }
            }
        });


        pwnew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!pwnew.getText().toString().equals("")){
                        if(pwnew.getText().toString().length() < 6){
                            sw = false;
                            input_layout_pwnew.setError(getString(R.string.hint_err_password));
                        }else {
                            p = true;
                            input_layout_pwnew.setErrorEnabled(false);
                        }

                    }
                }
            }
        });

        pwconfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!pwconfirm.getText().toString().equals("")){
                        if(!pwconfirm.getText().toString().trim().equals(pwnew.getText().toString().trim())){
                            pa = false;
                            input_layout_pwconfirm.setError(getString(R.string.hint_err_password2));
                        }else {
                            pa = true;
                            input_layout_pwconfirm.setErrorEnabled(false);
                        }

                    }
                }
            }
        });



        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setView(){
        Drawable toolbarBackground = toolbar.getBackground();
        int tColor = ((ColorDrawable)toolbarBackground).getColor();
        btcd.setBackgroundColor(tColor);
        btcp.setBackgroundColor(tColor);
        btccp.setBackgroundColor(tColor);
        btccd.setBackgroundColor(tColor);


        inset = new TransitionSet()
                .addTransition(new Scale(0.7f))
                .addTransition(new Fade());


        outset = new TransitionSet()
                .addTransition(new Fade());

    }
}

