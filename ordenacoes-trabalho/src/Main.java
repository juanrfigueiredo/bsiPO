import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
* @project - ImplementacaoOrdenacoes
* @author - juanr
*/

public class Main
{
    static final int n = 13;
    private Arquivo ordenado, reverso, randomico, auxreverso, auxrandomico;
    private FileWriter txt;
    private PrintWriter escritor;
    private int tini, tfim, mov, com;
    private Lista lordenada, lreversa, lrandomica, lauxrandomica, lauxreversa;

    private void initArquivos()
    {
        this.ordenado = new Arquivo("Odenado.dat");
        this.reverso = new Arquivo("Reverso.dat");
        this.randomico = new Arquivo("Randomico.dat");
        this.auxrandomico = new Arquivo();
        this.auxreverso = new Arquivo();
        try
        {
            this.txt = new FileWriter("tabela.txt");
            this.escritor = new PrintWriter(txt);
        }
        catch(Exception e)
        {
            System.out.println("Erro ao criar arquivo TXT");
            System.exit(-1);
        }
    }

    private void escreveCabecalho()
    {
        escritor.println("|     ORDENAÇÃO     |ARQUIVO ORDENADO\t\t\t|ARQUIVO  REVERSO\t\t\t"
                + "|ARQUIVO RANDÔMICO");
        escritor.println("|\t\t         |Comp. 1\t|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|Comp. 1\t|Comp. 2|Mov. 1\t|Mov. 2|Tempo\t|"
                + "Comp. 1\t|Comp. 2|Mov. 1\t|Mov. 2|Tempo\t|");
    }

    private void escreveTabela(String nomeMetodo, int cp, double ce, int mp, double me, double tempo)
    {
        escritor.printf("%s %d\t| %.0f\t| %d\t| %.0f\t| %.0f\t|", nomeMetodo, cp, ce, mp, me, tempo);
    }

    private void insercaoDireta()
    {
        //INSERÇÃO DIRETA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.insertionSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Insertion Sort\t     |", com, n - 1, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.insertionSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2) + n - 4) / 4, mov, (Math.pow(n, 2) + 3 * n - 4) / 2 , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.insertionSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2) + n - 2) / 4, mov, (Math.pow(n, 2) + 9 * n - 10) / 4, tfim - tini);
        escritor.println("\n");
    }

    private void insercaoBinaria()
    {
        //INSERÇÃO BINÁRIA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.insercao_binaria();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Binary Insertion    |", com, 0, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.insercao_binaria();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, (Math.pow(n, 2) + 3 * n - 4) / 2 , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.insercao_binaria();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, n * (Math.log(n)), mov, (Math.pow(n, 2) + 9 * n - 10) / 2 , tfim - tini);
        escritor.println("\n");
    }

    private void selecaoDireta()
    {
        //SELEÇÃO DIRETA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.selecao_direta();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Select Sort\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.selecao_direta();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / 4 + (3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.selecao_direta();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f), tfim - tini);
        escritor.println("\n");
    }

    private void ordenaBolha()
    {
        //BOLHA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.bolha();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Bubble Sort\t     |", com, 0, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.bolha();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.bolha();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }

    private void shakeSort()
    {
        //SHAKE
        //Bolha leve pra esquerda, bolha pesada pra direita
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.shake();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Shake Sort\t     |", com, (Math.pow(n, 2) - n) / 2, mov, 0 , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.shake();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2) - n) / 2, mov, 3 * (Math.pow(n, 2) - n) / 4, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.shake();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2) - n) / 2, mov, 3 * (Math.pow(n, 2) - n) / 2, tfim - tini);
        escritor.println("\n");
    }

    private void shellSort()
    {
        //SHELL
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.shell();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Shell Sort\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.shell();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.shell();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }

    private void heapSort()
    {
        //HEAP
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.heap();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Heap Sort\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.heap();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.heap();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }

    private void quickSortI()
    {
        //QUICK SEM PIVO
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.quick1();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Quick Sort 1\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.quick1();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.quick1();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }

    private void quickSortII()
    {
        //QUICK COM PIVO
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.quick2();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Quick Sort 2\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.quick2();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.quick2();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }

    private void mergeI()
    {
        //MERGE 1ª Implementação
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.merge1();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Merge 1\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.merge1();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.merge1();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");

    }

    private void mergeII()
    {
        //MERGE 2ª Implementação
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.merge2();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Merge 2\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.merge2();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.merge2();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");

    }

    private void counting()
    {
        //Counting
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.countingSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|CountingSort\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.countingSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.countingSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");
    }

    private void bucket()
    {
        //Bucket
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.bucket();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Gnome Sort\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.bucket();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.bucket();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");
    }

    private void radix()
    {
        //Radix
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.radix();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Radix\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.radix();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.radix();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");
    }

    private void comb()
    {
        //Comb
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.comb();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Comb\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.comb();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.comb();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");
    }

    private void gnome()
    {
        //Gnome
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.gnome();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Gnome Sort\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.gnome();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.gnome();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");
    }

    private void tim()
    {
        //Tim
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.tim();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Tim Sort\t     |", com, -1, mov, -1, tfim - tini);

        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso.getFile());
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.tim();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);

        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico.getFile());
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.tim();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n");
    }

    private void initLista()
    {
        lauxrandomica = new Lista();
        lauxreversa = new Lista();
        lordenada = new Lista();
        lreversa = new Lista();
        lrandomica = new Lista();
    }

    public void geraLista()
    {
        initLista();
        lordenada.geraOrdenada();
        lreversa.geraReversa();
        lrandomica.geraRandomica();

        lrandomica.exibeLista(); System.out.println("");
        lrandomica.quickSortS();
        lrandomica.exibeLista();
    }

    public void gerarTabela() throws IOException
    {
        initArquivos();
        escreveCabecalho();
        ordenado.geraArquivoOrdenado();
        reverso.geraArquivoReverso();
        randomico.geraArquivoRandomico();

        insercaoDireta(); //OK
        System.out.println("insercaoDireta - OK");
        insercaoBinaria(); //OK
        System.out.println("insercaoBinaria - OK");
        selecaoDireta(); //OK
        System.out.println("selecaoDireta - OK");
        ordenaBolha(); //Ok
        System.out.println("ordenaBolha - OK");
        shakeSort(); //Ok
        System.out.println("shake - OK");
        shellSort(); //Ok
        System.out.println("sort - OK");
        heapSort(); //Ok
        System.out.println("heap - OK");
        quickSortI(); //Ok
        System.out.println("quick I - OK");
        quickSortII(); //Ok
        System.out.println("quick II - OK");
//        mergeI(); //Ok ~
        System.out.println("merge I- N");
//        mergeII(); //Ok ~
        System.out.println("merge II - N");
        counting(); //Ok
        System.out.println("conting - OK");
        bucket(); //Ok
        System.out.println("bucket - OK");
        radix(); //Ok
        System.out.println("radix - OK");
        comb(); // OK
        System.out.println("comb - OK");
        gnome(); //Ok
        System.out.println("gnome - OK");
        tim(); // OK
        System.out.println("tim - OK");

        ordenado.exibirArq(); System.out.println("");
        auxreverso.exibirArq(); System.out.println("");
        auxrandomico.exibirArq();
        txt.close();
    }

    public static void main(String[] args) throws IOException
    {
        Main menu = new Main();
        menu.gerarTabela();

    }
}
