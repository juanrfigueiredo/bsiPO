import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

class Arquivo
{
    private String nomearquivo;
    private RandomAccessFile arquivo;
    private int comp, mov;

    public Arquivo(String nomearquivo)
    {
        try
        {
            arquivo = new RandomAccessFile(nomearquivo, "rw");
        }
        catch (IOException e) { }
    }

    public Arquivo() { }

    public void copiaArquivo(RandomAccessFile arquivoOrigem)
    {
        try
        {
            Registro reg = new Registro();
            int i = 0, tam = (int) arquivoOrigem.length() / Registro.length();
            this.arquivo = new RandomAccessFile("temp.dat", "rw");
            truncate(0);
            arquivoOrigem.seek(0);
            while(i < tam)
            {
                reg.leDoArq(arquivoOrigem);
                reg.gravaNoArq(arquivo);
                i++;
            }
        }
        catch(IOException e)
        {

        }
    }

    public RandomAccessFile getFile()
    {
        return arquivo;
    }

    public void truncate(long pos)
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        }
        catch (IOException exc) { }
    }

    public boolean eof()
    {
        boolean retorno = false;
        try
        {
            if (arquivo.getFilePointer() == arquivo.length())
                retorno = true;
        }
        catch (IOException e) { }

        return retorno;
    }

    public int filesize()
    {
        try
        {
            return (int) (arquivo.length() / Registro.length());
        }
        catch(IOException e)
        {
            return -1;
        }
    }

    public void initComp()
    {
        this.comp = 0;
    }

    public void initMov()
    {
        this.mov = 0;
    }

    public int getComp()
    {
        return comp;
    }

    public int getMov()
    {
        return mov;
    }

    public void exibirArq()
    {
        Registro aux = new Registro();
        seekArq(0);
        while (!this.eof())
        {
            aux.leDoArq(arquivo);
            aux.exibirReg();
        }
    }

    public void seekArq(int pos)
    {
        try
        {
            arquivo.seek(pos * Registro.length());
        }
        catch (IOException e){ }
    }

    // Geração de arquivos aleatórios

    public void geraArquivoOrdenado()
    {
        int j;
        for(j = 0; j < Main.n; j++)
        {
            Registro reg = new Registro(j);
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraArquivoReverso()
    {
        int j;
        for(j = 0; j < Main.n; j++)
        {
            Registro reg = new Registro(Main.n - j - 1);
            reg.gravaNoArq(arquivo);
        }
    }

    public void geraArquivoRandomico()
    {
        Random rand = new Random();
        int j;
        for(j = 0; j < Main.n; j++)
        {
            Registro reg = new Registro(rand.nextInt(Main.n));
            reg.gravaNoArq(arquivo);
        }
    }

    // Métodos de Ordenação

    public void selecao_direta()
    {
        Registro aux = new Registro(), reg = new Registro();
        int pos, i, j, tl = filesize();

        for(i = 0; i < tl - 1; i++)
        {
            pos = i;
            seekArq(pos);
            reg.leDoArq(arquivo);

            j = i + 1;
            while(j < tl)
            {
                seekArq(j);
                aux.leDoArq(arquivo);

                comp++;
                if(aux.getNumero() < reg.getNumero())
                {
                    pos = j;
                    seekArq(pos);
                    reg.leDoArq(arquivo);
                }

                j++;
            }

            seekArq(i);
            aux.leDoArq(arquivo);

            mov++;
            seekArq(pos);
            aux.gravaNoArq(arquivo);

            mov++;
            seekArq(i);
            reg.gravaNoArq(arquivo);
        }
    }

    public void insertionSort()
    {
        insercao_direta(0, filesize());
    }

    private void insercao_direta(int ini, int fim)
    {
        Registro regaux = new Registro(), regpos1 = new Registro();
        int i = ini + 1, tl = fim, pos;

        while(i < tl)
        {
            pos = i;

            seekArq(pos - 1);
            regpos1.leDoArq(arquivo);
            regaux.leDoArq(arquivo);

            comp++;
            while(pos > 0 && regaux.getNumero() < regpos1.getNumero())
            {
                mov++;
                seekArq(pos);
                regpos1.gravaNoArq(arquivo);

                pos--;

                seekArq(pos - 1);
                regpos1.leDoArq(arquivo);

                comp++;
            }

            mov++;
            seekArq(pos);
            regaux.gravaNoArq(arquivo);

            i++;
        }
    }

    public void insercao_binaria()
    {
        Registro regAux = new Registro(), regPos1 = new Registro(), regPos = new Registro();
        int i, tl = filesize(), pos;

        i = 1;
        while(i < tl)
        {
            seekArq(i);
            regAux.leDoArq(arquivo);

            pos = buscaBinaria(regAux.getNumero(), i);

            for(int j = i; j > pos; j--)
            {
                seekArq(j - 1);
                regPos1.leDoArq(arquivo);
                regPos.leDoArq(arquivo);

                mov += 2;
                seekArq(j - 1);
                regPos.gravaNoArq(arquivo);
                regPos1.gravaNoArq(arquivo);
            }
            i++;
        }
    }

    private int buscaBinaria(int elemento, int tl)
    {
        int ini = 0, fim = tl - 1, meio = fim / 2;
        Registro reg = new Registro();

        seekArq(meio);
        reg.leDoArq(arquivo);

        comp++;
        while(meio != ini && elemento != reg.getNumero())
        {
            comp++;
            if(elemento < reg.getNumero())
                fim = meio;
            else
                ini = meio;

            meio = (ini + fim) / 2;
            seekArq(meio);
            reg.leDoArq(arquivo);
            comp++;
        }


        comp++;
        seekArq(tl - 1);
        reg.leDoArq(arquivo);
        if(elemento > reg.getNumero())
            return tl;

        comp++;
        seekArq(meio);
        reg.leDoArq(arquivo);
        if(elemento > reg.getNumero())
            return meio + 1;

        return meio;
    }

    public void bolha()
    {
        Registro regA = new Registro(), regB = new Registro();
        int tl = filesize(), i = 0;

        while(tl > 1)
        {
            i = 0;
            while(i < tl - 1)
            {
                seekArq(i);
                regA.leDoArq(arquivo);
                regB.leDoArq(arquivo);

                comp++;
                if(regB.getNumero() < regA.getNumero())
                {
                    mov += 2;
                    seekArq(i);
                    regB.gravaNoArq(arquivo);
                    regA.gravaNoArq(arquivo);
                }

                i++;
            }
            tl--;
        }
    }

    public void shake()
    {
        int ini = 0, fim = filesize(), i;
        Registro regA = new Registro(), regB = new Registro();

        while(ini < fim)
        {
            for(i = ini; i < fim - 1; i++)
            {
                seekArq(i);
                regA.leDoArq(arquivo);
                regB.leDoArq(arquivo);

                comp++;
                if(regA.getNumero() > regB.getNumero())
                {
                    mov += 2;
                    seekArq(i);
                    regB.gravaNoArq(arquivo);
                    regA.gravaNoArq(arquivo);
                }
            }
            for(; i > ini; i--)
            {
                seekArq(i - 1);
                regA.leDoArq(arquivo);
                regB.leDoArq(arquivo);

                comp++;
                if(regB.getNumero() < regA.getNumero())
                {
                    mov += 2;
                    seekArq(i - 1);
                    regB.gravaNoArq(arquivo);
                    regA.gravaNoArq(arquivo);
                }
            }

            fim--;
            ini++;
        }
    }

    public void heap()
    {
        Registro regA = new Registro(), regB = new Registro();
        int tl = filesize(), pai, fd, fe, maiorf;

        while(tl > 1)
        {
            for(pai = tl / 2 - 1; pai >= 0; pai--)
            {
                fe = pai + pai + 1;
                fd = fe + 1;

                maiorf = fe;

                seekArq(fe);
                regA.leDoArq(arquivo);

                seekArq(fd);
                regB.leDoArq(arquivo);

                comp++;
                if(fd < tl && regB.getNumero() > regA.getNumero())
                    maiorf = fd;

                seekArq(pai);
                regA.leDoArq(arquivo);

                seekArq(maiorf);
                regB.leDoArq(arquivo);

                comp++;
                if(regB.getNumero() > regA.getNumero())
                {
                    mov++;
                    seekArq(pai);
                    regB.gravaNoArq(arquivo);

                    mov++;
                    seekArq(maiorf);
                    regA.gravaNoArq(arquivo);
                }
            }

            seekArq(0);
            regA.leDoArq(arquivo);

            seekArq(tl - 1);
            regB.leDoArq(arquivo);

            mov++;
            seekArq(0);
            regB.gravaNoArq(arquivo);

            mov++;
            seekArq(tl - 1);
            regA.gravaNoArq(arquivo);

            tl--;
        }
    }

    public void shell()
    {
        Registro reg1 = new Registro(), reg2 = new Registro();
        int i, j, k, dist, tl = filesize();

        for(dist = 4; dist > 0; dist /= 2)
        {
            for(i = 0; i < dist; i++)
            {
                for(j = i; j + dist < tl; j += dist)
                {
                    seekArq(j);
                    reg1.leDoArq(arquivo);
                    seekArq(j + dist);
                    reg2.leDoArq(arquivo);

                    comp++;
                    if(reg1.getNumero() > reg2.getNumero())
                    {
                        mov += 2;
                        seekArq(j);
                        reg2.gravaNoArq(arquivo);
                        seekArq(j + dist);
                        reg1.gravaNoArq(arquivo);

                        k = j;

                        if(k - dist >= 0)
                        {
                            comp++;

                            seekArq(k);
                            reg1.leDoArq(arquivo);
                            seekArq(k - dist);
                            reg2.leDoArq(arquivo);
                        }

                        for(; k >= 0 && reg1.getNumero() < reg2.getNumero(); k -= dist)
                        {
                            mov += 2;
                            seekArq(k);
                            reg2.gravaNoArq(arquivo);
                            seekArq(k - dist);
                            reg1.gravaNoArq(arquivo);

                            if(k - dist >= 0)
                            {
                                comp++;

                                seekArq(k);
                                reg1.leDoArq(arquivo);
                                seekArq(k - dist);
                                reg2.leDoArq(arquivo);
                            }
                        }
                    }
                }
            }
        }
    }

    public void quick1()
    {
        quicksp(0, filesize() - 1);
    }

    private void quicksp(int ini, int fim)
    {
        int i = ini, j = fim;
        Registro reg1 = new Registro(), reg2 = new Registro();

        while(i < j)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);

            while(i < j && reg1.getNumero() <= reg2.getNumero())
            {
                comp++;

                i++;
                seekArq(i);
                reg1.leDoArq(arquivo);
            }


            if(i < j)
            {
                mov += 2;
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
            }

            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);

            while(j > i && reg2.getNumero() >= reg1.getNumero())
            {
                comp++;

                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
            }


            if(i < j)
            {
                mov += 2;

                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);
            }
        }

        if(ini < j - 1)
            quicksp(ini, j - 1);

        if(fim > i + 1)
            quicksp(j + 1, fim);

    }

    public void quick2()
    {
        quickcp(0, filesize() - 1);
    }

    private void quickcp(int ini, int fim)
    {
        int i = ini, j = fim;
        Registro reg1 = new Registro(), reg2 = new Registro(), pivo = new Registro();

        seekArq((ini + fim) / 2);
        pivo.leDoArq(arquivo);

        while(i < j)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);

            comp ++;
            while(reg1.getNumero() < pivo.getNumero())
            {
                i++;
                seekArq(i);
                reg1.leDoArq(arquivo);

                comp ++;
            }
            seekArq(j);
            reg2.leDoArq(arquivo);

            comp ++;
            while(reg2.getNumero() > pivo.getNumero())
            {
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);

                comp ++;
            }

            if(i <= j)
            {
                mov += 2;

                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg1.gravaNoArq(arquivo);

                i++; j--;
            }
        }

        if(ini < j)
            quickcp(ini, j);

        if(fim > i)
            quickcp(i, fim);

    }

    public void merge1()
    {
        int seq = 1, tl = filesize(), meio = tl / 2;
        int i = 0, j = 0, k = 0, aux_seq = seq, aux_seq2 = seq;
        Registro[] vet1 = new Registro[tl / 2];
        Registro[] vet2 = new Registro[tl / 2];
        Registro reg1, reg2;

        while(seq < tl)
        {
            //Partição ---------------------------------------------------------

            for(i = 0; i < meio; i++)
            {
                reg1 = new Registro();
                reg2 = new Registro();

                seekArq(i);
                reg1.leDoArq(arquivo);
                seekArq(i + meio);
                reg2.leDoArq(arquivo);

                vet1[i] = reg1;
                vet2[i] = reg2;
            }
            //------------------------------------------------------------------
            // Fusão -----------------------------------------------------------
            k = i = j = 0;
            aux_seq = seq;
            aux_seq2 = seq;
            while(k < tl - 1)
            {
                while(i < 6 && j < 6)
                {
                    comp++;
                    if(vet1[i].getNumero() < vet2[j].getNumero())
                    {
                        mov++;
                        seekArq(k);
                        vet1[i].gravaNoArq(arquivo);
                        i++;
                    }
                    else
                    {
                        mov++;
                        seekArq(k);
                        vet2[j].gravaNoArq(arquivo);
                        j++;
                    }
                    k++;
                }

                while(i < 6 )
                {
                    mov++;
                    seekArq(k);
                    vet1[i].gravaNoArq(arquivo);
                    i++; k++;
                }

                while(j < 6)
                {
                    mov++;
                    seekArq(k);
                    vet2[j].gravaNoArq(arquivo);
                    j++; k++;
                }

                aux_seq2 += aux_seq;
            }
            //------------------------------------------------------------------
            seq += seq;
        }
    }

    public void merge2()
    {
        int a = filesize();
        merge_2(new Registro[a], 0, a-1);
    }

    private void merge_2(Registro[] aux, int esq, int dir)
    {
        int meio;

        if(esq < dir)
        {
            meio = (esq + dir) / 2;
            merge_2(aux, esq, meio);
            merge_2(aux, meio + 1, dir);
            fusao(aux, esq, meio, meio + 1, dir);
        }
    }

    private void fusao(Registro[] aux, int ini1, int fim1, int ini2, int fim2)
    {
        int k = 0, i = ini1, j = ini2;
        Registro reg1, reg2;
        while(i <= fim1 && j <= fim2)
        {
            reg1 = new Registro();
            reg2 = new Registro();

            seekArq(i);
            reg1.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);

            comp++;
            if(reg1.getNumero() < reg2.getNumero())
            {
                aux[k] = reg1;
                i++;
            }
            else
            {
                aux[k] = reg2;
                j++;
            }
            k++;
        }

        while(i <= fim1)
        {
            seekArq(i);
            reg1 = new Registro();
            reg1.leDoArq(arquivo);

            aux[k] = reg1;
            k++; i++;
        }

        while(j <= fim2)
        {
            seekArq(j);
            reg2 = new Registro();
            reg2.leDoArq(arquivo);

            aux[k] = reg2;
            k++; j++;
        }

        for(i = 0; i+ini1 < k; i++)
        {
            mov++;
            aux[i + ini1].gravaNoArq(arquivo);
        }
    }

    public void countingSort()
    {
        int range = Main.n, TL = filesize(), i, aux;
        Registro reg = new Registro();
        Registro aux_arq[] = new Registro[TL];

        int count[] = new int[range];

        //contar os elementos
        for(i = 0; i < TL; i++)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            count[reg.getNumero()]++;
        }

        //arrumar o vetor de contador
        for(i = 0; i < range - 1; i++)
            count[i + 1] += count[i];

        //ordenando em um array auxiliar
        for(i = TL - 1; i >= 0; i--)
        {
            seekArq(i);
            reg.leDoArq(arquivo);
            aux_arq[--count[reg.getNumero()]] = reg;
            reg = new Registro();
        }

        //gravando no arquivo
        seekArq(0);
        for(i = 0; i < TL; i++)
        {
            mov++;
            aux_arq[i].gravaNoArq(arquivo);
        }
    }

    public void gnome()
    {
        int i = 0, tl = filesize(), j;
        Registro reg1 = new Registro(), reg2 = new Registro();

        for(i = 0; i < tl - 1; i++)
        {
            seekArq(i);
            reg1.leDoArq(arquivo);
            reg2.leDoArq(arquivo);

            comp++;
            if(reg1.getNumero() > reg2.getNumero())
            {
                j = i;

                comp++;
                while(j >= 0 && reg2.getNumero() < reg1.getNumero())
                {
                    mov += 2;
                    seekArq(j);
                    reg2.gravaNoArq(arquivo);
                    reg1.gravaNoArq(arquivo);

                    j--;
                    if(j >= 0)
                    {
                        seekArq(j);
                        reg1.leDoArq(arquivo);
                        reg2.leDoArq(arquivo);
                        comp++;
                    }
                }
            }
        }
    }

    public void bucket()
    {
        Registro mat[][] = new Registro[10][Main.n];
        int[] index = new int[10];
        Registro reg;
        int i, tl = filesize(), j, k;

        // Insere os elementos na lista
        for(i = 0; i < tl; i++)
        {
            reg = new Registro();
            seekArq(i);
            reg.leDoArq(arquivo);
            mat[(int) ((reg.getNumero() / (float) Main.n) * 10)][index[(int) ((reg.getNumero() / (float) Main.n) * 10)]++] = reg;
        }

        seekArq(0);
        for(i = 0; i < 10; i++)
        {
            j = 1;
            while(j < index[i])
            {
                k = j;
                comp++;
                while(mat[i][k].getNumero() > mat[i][k - 1].getNumero())
                {
                    reg = mat[i][k];
                    mat[i][k] = mat[i][k - 1];
                    mat[i][k - 1] = reg;
                    comp++;
                }
                j++;
            }

            for(j = 0; j < index[i]; j++)
            {
                mov++;
                mat[i][j].gravaNoArq(arquivo);
            }
        }
    }

    public void radix()
    {
        int i, j, max = Main.n, TL = filesize();
        Registro reg, vet_aux[] = new Registro[TL];
        int count[];

        seekArq(0);
        for(i = 1; i < max; i *= 10)
        {
            count = new int[10];

            //contando os elementos
            seekArq(0);
            for(j = 0; j < TL; j++)
            {
                reg = new Registro();
                reg.leDoArq(arquivo);
                count[(reg.getNumero() / i) % 10]++;
            }

            //arrumar o vetor de contador
            for(j = 0; j < 9; j++)
                count[j + 1] += count[j];

            //ordenar no vetor auxiliar
            for(j = TL - 1; j >= 0; j--)
            {
                reg = new Registro();
                seekArq(j);
                reg.leDoArq(arquivo);
                vet_aux[--count[(reg.getNumero() / i) % 10]] = reg;
            }

            //gravar no arquivo
            seekArq(0);
            for(j = 0; j < TL; j++)
            {
                mov++;
                vet_aux[j].gravaNoArq(arquivo);
            }
        }
    }

    public void comb()
    {
        int i = 0, tl = filesize(), fator = (int) (tl / 1.3);
        Registro reg1 = new Registro(), reg2 = new Registro();

        while(fator > 0 && i != tl - 1)
        {
            i = 0;
            while(i + fator < tl)
            {
                seekArq(i);
                reg1.leDoArq(arquivo);
                seekArq(fator + i);
                reg2.leDoArq(arquivo);

                comp++;
                if(reg1.getNumero() > reg2.getNumero())
                {
                    mov += 2;
                    seekArq(i);
                    reg2.gravaNoArq(arquivo);
                    seekArq(fator + i);
                    reg1.gravaNoArq(arquivo);
                }
                i++;
            }
            fator = (int) (fator / 1.3);
        }
    }

    public void tim()
    {
        int i, j, tl = filesize(), min = calculaMinRun(tl);


    }

    private int calculaMinRun(int n)
    {
        int f = 0;

        while(n >= 64)
        {
            f = (f | n) & 1;
            n++;
        }
        return f + n;
    }
}
