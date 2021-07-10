package zako;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import zako.dictionary.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    static final String API_KEY="dict.1.1.20210709T174942Z.00f9041b1daf21a6.983e89cebbdf8c126d88accb03b9f6c91e3c1241";

    public static List<String> getLang()
    {
        final String URL="https://dictionary.yandex.net/api/v1/dicservice.json/getLangs?key=";
        List<String>langs=new ArrayList<String>();
        URL url=null;
        URLConnection urlConnection=null;
        BufferedReader reader=null;
        Gson gson=new Gson();

        try {
            url=new URL(URL+API_KEY);
            urlConnection=url.openConnection();
            reader=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            Type type=new TypeToken<ArrayList<String>>(){}.getType();
            langs=gson.fromJson(reader,type);
            return langs;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
    public static Dictionary getWord(String word,String lang)
    {
        Dictionary dictionary=new Dictionary();
        URL url=null;
        URLConnection urlConnection=null;
        Gson gson=new Gson();
        BufferedReader reader=null;

        try {
            url=new URL("https://dictionary.yandex.net/api/v1/dicservice.json/lookup?key="+API_KEY+"&lang="+lang+"&text="+word);
            urlConnection=url.openConnection();
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            Type type=new TypeToken<Dictionary>(){}.getType();
            dictionary=gson.fromJson(reader,type);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dictionary;
    }
    public static void show(Dictionary dictionary)
    {
        Def def[]=dictionary.getDef();
        Tr tr[];
        for (int i = 0; i < def.length; i++) {
            System.out.println(def[i].getText()+" "+def[i].getPos());
            tr=def[i].getTr();

            for (int i1 = 0; i1 < tr.length; i1++) {
                System.out.println(tr[i1].getText()+" "+tr[i1].getPos());
                Mean []mean=tr[i1].getMean();
                Syn []syn=tr[i1].getSyn();
                Ex []ex=tr[i1].getEx();

                if (mean!=null)
                {
                    for (int i2 = 0; i2 < mean.length; i2++) {
                        System.out.println(mean[i2].getText());
                    }
                }

                if (ex!=null)
                {
                    for (int i2 = 0; i2 < ex.length; i2++) {
                        System.out.println(ex[i2].getText());
                        Tr1 tr1[]=ex[i2].getTr();

                        for (int i3 = 0; i3 < tr1.length; i3++) {
                            System.out.println(tr1[i3].getText());
                        }
                    }
                }

                if (syn!=null)
                {
                for (int i2 = 0; i2 < syn.length; i2++) {
                    System.out.println(syn[i].getText());
                }

                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        String word,lang;
        Dictionary dictionary;

        List<String>langs=getLang();
        for (int i = 0; i < langs.size(); i++) {
            System.out.print((i+1)+") "+langs.get(i)+"\t\t\t");
            if ((i+1)%8==0)
                System.out.println();
        }
        System.out.println();
        System.out.print("Tilni tanlang ");
        int k=scanner.nextInt()-1;

        System.out.print("So'zni kiriting : ");
        word=scanner.next();

        lang=langs.get(k);
        dictionary=getWord(word,lang);
        show(dictionary);

        //System.out.println(word+" "+lang);

    }
}
