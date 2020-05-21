package com.ansoft.bfit.Fragments.Home;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ansoft.bfit.Database.SPDataManager;
import com.ansoft.bfit.R;
import com.yarolegovich.lovelydialog.LovelyTextInputDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {

    private RelativeLayout prefCountDown, prefRestTime, prefGender, prefYearOfBirth, prefHeight, prefWeight;
    private Switch switchSound, switchVoice;
    private TextView tvCountDown, tvRestTime, tvGender, tvYearOfBirth, tvHeight, tvWeight;
    private RelativeLayout btnRateUs, btnFeedback;
    private TextView tvVersion;

    public SettingsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        connectViews(view);
        loadData();
        loadVersionInfo();
        implementClickListener();
        return view;
    }

    private void implementClickListener() {
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND, Uri.parse("mailto:bfittapps@gmail.com"));

                String manufacturer = Build.MANUFACTURER;
                String model = Build.MODEL;
                int version = Build.VERSION.SDK_INT;
                String versionRelease = Build.VERSION.RELEASE;
                String text = "\n\n\nFrom:\nManufacturer : " + manufacturer + "\nModel : " + model + "\n" + "Version : " + version + "\nRelease : " + versionRelease;
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name) + " Feedback");
                intent.putExtra(Intent.EXTRA_TEXT, text);
                intent.putExtra(Intent.EXTRA_EMAIL, "bfittapps@gmail.com");
                startActivity(Intent.createChooser(intent, "Send Feedback"));
            }
        });

        btnRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        switchSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SPDataManager.setSound(1, getActivity());
                } else {
                    SPDataManager.setSound(0, getActivity());
                }
            }
        });

        switchVoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    SPDataManager.setVoice(1, getActivity());
                } else {
                    SPDataManager.setVoice(0, getActivity());
                }
            }
        });


        prefCountDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new LovelyTextInputDialog(getActivity(), R.style.EditTextTintTheme)
                        .setTopColorRes(R.color.colorAccent)
                        .setTitle("CountDown Timer")
                        .setIcon(R.drawable.ic_timer_black_24dp)
                        .setIconTintColor(getActivity().getColor(R.color.white))
                        .setInitialInput(SPDataManager.getCountDown(getActivity()) + "")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setInputFilter("Please enter valid number between 1 and 10", new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                try {
                                    int sec = Integer.parseInt(text);
                                    if (sec < 0 || sec > 10) {
                                        return false;
                                    }
                                    return true;
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        })
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {

                                SPDataManager.setCountDown(Integer.parseInt(text), getActivity());
                                loadData();
                            }
                        })
                        .show();


            }
        });


        prefRestTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new LovelyTextInputDialog(getActivity(), R.style.EditTextTintTheme)
                        .setTopColorRes(R.color.colorAccent)
                        .setTitle("Rest Timer")
                        .setIcon(R.drawable.ic_av_timer_black_24dp)
                        .setIconTintColor(getActivity().getColor(R.color.white))
                        .setInitialInput(SPDataManager.getRestTime(getActivity()) + "")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setInputFilter("Please enter valid number between 1 and 60", new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                try {
                                    int sec = Integer.parseInt(text);
                                    if (sec < 0 || sec > 60) {
                                        return false;
                                    }
                                    return true;
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        })
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {

                                SPDataManager.setRestTime(Integer.parseInt(text), getActivity());
                                loadData();
                            }
                        })
                        .show();


            }
        });


        prefYearOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new LovelyTextInputDialog(getActivity(), R.style.EditTextTintTheme)
                        .setTopColorRes(R.color.colorAccent)
                        .setTitle("Year of Birth")
                        .setIcon(R.drawable.ic_date_range_black_24dp)
                        .setIconTintColor(getActivity().getColor(R.color.white))
                        .setInitialInput(SPDataManager.getBirthYear(getActivity()) + "")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setInputFilter("Please enter valid number between 1990 and 2019", new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                try {
                                    int sec = Integer.parseInt(text);
                                    if (sec < 1990 || sec > 2019) {
                                        return false;
                                    }
                                    return true;
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        })
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {

                                SPDataManager.setBirthYear(Integer.parseInt(text), getActivity());
                                loadData();
                            }
                        })
                        .show();


            }
        });


        prefHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new LovelyTextInputDialog(getActivity(), R.style.EditTextTintTheme)
                        .setTopColorRes(R.color.colorAccent)
                        .setTitle("Height (Cm)")
                        .setIcon(R.drawable.ic_menu_height)
                        .setIconTintColor(getActivity().getColor(R.color.white))
                        .setInitialInput(SPDataManager.getHeightCm(getActivity()) + "")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setInputFilter("Please enter valid number between 0 and 300", new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                try {
                                    int sec = Integer.parseInt(text);
                                    if (sec < 0 || sec > 300) {
                                        return false;
                                    }
                                    return true;
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        })
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {

                                SPDataManager.setHeightCm(Integer.parseInt(text), getActivity());
                                loadData();
                            }
                        })
                        .show();


            }
        });


        prefWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new LovelyTextInputDialog(getActivity(), R.style.EditTextTintTheme)
                        .setTopColorRes(R.color.colorAccent)
                        .setTitle("Weight (kg)")
                        .setIcon(R.drawable.ic_menu_weight)
                        .setIconTintColor(getActivity().getColor(R.color.white))
                        .setInitialInput(SPDataManager.getWeightKg(getActivity()) + "")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setInputFilter("Please enter valid number between 0 and 300", new LovelyTextInputDialog.TextFilter() {
                            @Override
                            public boolean check(String text) {
                                try {
                                    int sec = Integer.parseInt(text);
                                    if (sec < 0 || sec > 300) {
                                        return false;
                                    }
                                    return true;
                                } catch (Exception e) {
                                    return false;
                                }
                            }
                        })
                        .setConfirmButton(android.R.string.ok, new LovelyTextInputDialog.OnTextInputConfirmListener() {
                            @Override
                            public void onTextInputConfirmed(String text) {

                                SPDataManager.setWeightKg(Integer.parseInt(text), getActivity());
                                loadData();
                            }
                        })
                        .show();


            }
        });
    }

    private void loadVersionInfo() {
        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            String version = pInfo.versionName;
            tvVersion.setText("Version : " + version);
        } catch (PackageManager.NameNotFoundException e) {
            tvVersion.setVisibility(View.GONE);
        }
    }

    private void connectViews(View view) {
        prefCountDown = view.findViewById(R.id.prefCountDown);
        prefRestTime = view.findViewById(R.id.prefRestTime);
        prefGender = view.findViewById(R.id.prefGender);
        prefYearOfBirth = view.findViewById(R.id.prefYearOfBirth);
        prefWeight = view.findViewById(R.id.prefWeight);
        prefHeight = view.findViewById(R.id.prefHeight);
        switchSound = view.findViewById(R.id.switchSound);
        switchVoice = view.findViewById(R.id.switchVoice);
        tvCountDown = view.findViewById(R.id.tvCountDown);
        tvRestTime = view.findViewById(R.id.tvRestTime);
        tvGender = view.findViewById(R.id.tvGender);
        tvYearOfBirth = view.findViewById(R.id.tvYearOfBirth);
        tvHeight = view.findViewById(R.id.tvHeight);
        tvWeight = view.findViewById(R.id.tvWeight);
        tvVersion = view.findViewById(R.id.tvVersion);
        btnFeedback = view.findViewById(R.id.btnFeedback);
        btnRateUs = view.findViewById(R.id.btnRateUs);
    }

    private void loadData() {
        if (SPDataManager.getSound(getActivity()) == 0) {
            switchSound.setChecked(false);
        } else {
            switchSound.setChecked(true);
        }
        if (SPDataManager.getVoice(getActivity()) == 0) {
            switchVoice.setChecked(false);
        } else {
            switchVoice.setChecked(true);
        }
        tvCountDown.setText(SPDataManager.getCountDown(getActivity()) + " Sec");
        tvRestTime.setText(SPDataManager.getRestTime(getActivity()) + " Sec");
        if (SPDataManager.getGender(getActivity()) == 0) {
            tvGender.setText("Male");
        } else {
            tvGender.setText("Female");
        }
        tvYearOfBirth.setText(SPDataManager.getBirthYear(getActivity()) + "");
        tvHeight.setText(SPDataManager.getHeightCm(getActivity()) + " Cm");
        tvWeight.setText(SPDataManager.getWeightKg(getActivity()) + " Kg");
    }
}
