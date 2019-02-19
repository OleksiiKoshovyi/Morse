package com.example.morze;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button bRead,bWrite;
    EditText tBox;

    private char[] alphabet = { 'А', 'Б', 'Ц', 'Д', 'Е', 'Ё', 'Ф', 'Г', 'Х', 'И', 'Й',
            'К', 'Л', 'М', 'Н', 'О', 'П', 'Щ', 'Р', 'С', 'Т', 'У', 'Ж', 'В',
            'Ь', 'Ъ', 'Ы', 'З', ' ', 'Ч', 'Ш', 'Э', 'Ю', 'Я'};

    private String[] morse = { ".-", "-...", "-.-.", "-..", ".", ".", "..-.", "--.",
            "....", "..", ".---", "-.-", ".-..", "--", "-.", "---", ".--.",
            "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "−−.−−", "-.--",
            "--..", "|" ,"---.","----", "..--..", "..--", ".-.-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bRead = findViewById(R.id.bRead);
        bWrite = findViewById(R.id.bWrite);
        tBox = findViewById(R.id.tBox);

        bRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = createFile(getFilesDir().getAbsolutePath()+"/Morse.txt");
                    tBox.setText(toAlphabet(readFile(file)));
                    Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        bWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File file = createFile(getFilesDir().getAbsolutePath()+"/Morse.txt");
                    writeToFile(file,toMorse(tBox.getText().toString()));
                    Toast.makeText(getBaseContext(),"Done",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String toMorse(String str){
        char[] chs = str.toUpperCase().toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char ch: chs){
            for (int i =0;i<alphabet.length;i++){
                if(ch == alphabet[i]){
                    sb.append(morse[i]+" ");
                    break;
                }
            }
        }
        return sb.toString();
    }

    private String toAlphabet(String str){
        String[] code = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for(String s: code){
            for (int i =0;i<morse.length;i++){
                if(morse[i].equals(s)){
                    sb.append(alphabet[i]);
                    break;
                }
            }
        }
        return sb.toString();
    }

    private void writeToFile(File file, String str){
        byte[] mass = str.getBytes();
        FileOutputStream stream = null;
        try{
            stream = new FileOutputStream(file.getAbsolutePath());
            stream.write(mass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(stream!=null){
                    stream.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private String readFile(File file){
        StringBuilder str = new StringBuilder();
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!=null){
                str.append(line);
                System.out.println("Text text : "+str+" : end");
                str.append('\n');
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return str.toString();
    }


    private File createFile(String path) throws IOException {
        File file = new File(path);
        if(!checkFile(file)) {
            createNewFile(file);
        }

        return file;
    }

    private boolean checkFile(File file){
        return file.exists();
    }

    private boolean createNewFile(File file) throws IOException {
        return file.createNewFile();
    }



}
