package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class VypocetNajkratsichOrientUVciest {
    private Integer[] P;

    private Integer[][] H;
    private Integer[] S;

    private Integer[] t;
    private Integer[] x;

    private int u;


    private int index;

    private int pocetHran;
    private int pocetVrcholov;
    private int pocetVrcholovZ;
    private String subor;

    public VypocetNajkratsichOrientUVciest(String subor, int u) throws FileNotFoundException {

        this.subor = subor;
        this.u = u;


        this.pocetHran = pocetHran(subor);
        this.H = new Integer[this.pocetHran + 1][3];

        this.dosadH(subor);

        this.pocetVrcholov = pocetVrcholovPoDosadeniH();
        S = new Integer[pocetVrcholovZ() + 1];
        dosadS();
        this.pocetVrcholovZ = pocetVrcholovZ();

        x = new Integer[pocetVrcholov + 1];
        t = new Integer[pocetVrcholov + 1];

        P = new Integer[pocetVrcholov+1];


        uvitanie();


        krok1();
        krok2();
        krok3();
        krok4();
        vypisVysledok();

//        vytlacPole(t);
//        vytlacPole(x);

    }

    public void vypisVysledok() {
        for (int i = 1; i < pocetVrcholov+1; i++) {
            if (i == u) continue;

            if (t[i] >= 107374191 ) {
                System.out.println("\t\tZ vrcholu: " + u + " nevedie ziadna cesta do vrcholu: " + i);
            } else {
                System.out.print("\t\tZ vrcholu: " + u + " do vrcholu " + i + ": " );
                System.out.println(t[i]);
            }
        }
    }

    public void vytlacPole(Integer[] pole) {
        System.out.println("tlacim pole");
        for (int i = 0; i < pole.length; i++) {
            System.out.println(pole[i]);
        }
        System.out.println();
    }



    public void krok1() throws FileNotFoundException {
        MonoOcislovanieAcDigrafu monoOcislovanieAcDigrafu = new MonoOcislovanieAcDigrafu(subor);

        for (int i = 1; i < pocetVrcholov + 1 ; i++) {
            P[i] = monoOcislovanieAcDigrafu.getMonoOcislovanie(i);
        }



        for (int i = 1; i < pocetVrcholov + 1 ; i++) {
            if(P[i] == u) {
                index = i ;
            }
        }

    }

    public void krok2() {
        for (int i = 1; i < pocetVrcholov + 1; i++) {
            t[i] = Integer.MAX_VALUE/2;
            x[i] = 0;
        }
        t[u] = 0;
    }

    public void krok3() {

        int doKedy;
        if (S[index] == pocetHran || index == pocetVrcholovZ) {
            //ak ma posledny vrchol len jednu hranu
            //alebo
            //ak je r posledny vrchol Z
            doKedy = pocetHran+1;

        }  else  {
            doKedy = S[index+1];
        }

        for (int i = S[index]; i < doKedy; i++) {
            int w = H[i][1];
                if (t[w] > t[index] + uvCena(index,w)) {
                    t[w] = t[index] + uvCena(index,w);
                    x[w] = index;
                }
        }

    }

    public void krok4() {

        while (true) {
            index++;
            if (index >= pocetVrcholovZ) {
                return;
            }
            krok3();
        }

    }

    public int uvCena(int u, int v) {
        int doKedy;
        if (S[u] == pocetHran || u == pocetVrcholovZ) {
            //ak ma posledny vrchol len jednu hranu
            //alebo
            //ak je r posledny vrchol Z
            doKedy = pocetHran+1;

        }  else  {
            doKedy = S[u+1];
        }

        for (int i = S[u]; i < doKedy; i++) {
            if (H[i][0] == u & H[i][1] == v) {
                return H[i][2];
            }
        }
        return 66666;
    }

    public void uvitanie() {
        System.out.println();
        System.out.println("\t***********Monotone Ocislovanie AcyklickehoGrafu + Vypocet nakratsich orientovanych u-v ciest***********");
        System.out.println("\t\tGraf ma " + pocetHran + " hran");
        System.out.println("\t\tGraf ma " + pocetVrcholov + " vrcholov");
        System.out.println();
    }

    public int pocetHran(String subor) throws FileNotFoundException {
        File hrany = new File(subor);
        Scanner scannerHrany = new Scanner(hrany);
        int lineNumber = 0;
        while (scannerHrany.hasNextLine()) {
            String line = scannerHrany.nextLine();
            lineNumber++;
        }

        return lineNumber;
    }

    public int pocetVrcholovPoDosadeniH() {
        int maxZ = 0;
        int maxDo = 1;
        for (int i = 1; i < pocetHran + 1; i++) {
            if (H[i][0] >= maxZ) maxZ = H[i][0];
            if (H[i][1] >= maxDo) maxDo = H[i][1];
        }

        if (maxZ >= maxDo) {
            return maxZ;
        } else {
            return maxDo;
        }


    }

    public int pocetVrcholovZ() {
        int maxZ = 0;

        for (int i = 1; i < pocetHran + 1; i++) {
            if (H[i][0] >= maxZ) maxZ = H[i][0];
        }

        return maxZ;

    }



    public void dosadS() {
        // S[x] pricom x je vrchol sa rovna, na ktorom riadku zacina vrchol x

        for (int i = 1; i < pocetHran + 1; i++) {
            if (!H[i][0].equals(H[i - 1][0])) {
                S[H[i][0]] = i;
            }
        }
    }

    public void dosadH(String subor) throws FileNotFoundException {
        File hrany = new File(subor);
        Scanner scannerHrany = new Scanner(hrany);

        int pocitadlo = 0;
        int lineNumber = 1;
        while (scannerHrany.hasNextInt()) {
            int cislo = scannerHrany.nextInt();

            this.H[lineNumber][pocitadlo] = cislo;
            pocitadlo++;

            if (pocitadlo == 3) {
                pocitadlo = 0;
                lineNumber++;
            }

        }

    }
}
