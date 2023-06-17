public class BTree implements Definicoes {
    private No raiz;

    public BTree() {
        raiz = null;
    }

    public No getRaiz() {
        return raiz;
    }

    public No navegarAteFolha(int info) {
        No folha = raiz;
        int pos;
        while (folha.getvLig(0) != null) {
            pos = folha.procurarPosicao(info);
            folha = folha.getvLig(pos);
        }
        return folha;
    }

    public No localizarPai(No folha, int info) {
        No pai = raiz;
        No aux = pai;
        int pos;
        while (aux != folha) {
            pai = aux;
            pos = aux.procurarPosicao(info);
            aux = aux.getvLig(pos);
        }
        return pai;
    }

    public void split(No folha, No pai) {
        No cx1 = new No();
        No cx2 = new No();
        int pos;
        for (int i = 0; i < M; i++) {
            cx1.setvInfo(i, folha.getvInfo(i));
            cx1.setvPos(i, folha.getvPos(i));
            cx1.setvLig(i, folha.getvLig(i));
        }
        cx1.setvLig(M, folha.getvLig(M));
        cx1.setTl(M);

        for (int i = M + 1; i < 2 * M + 1; i++) {
            cx2.setvInfo(i - (M + 1), folha.getvInfo(i));
            cx2.setvPos(i - (M + 1), folha.getvPos(i));
            cx2.setvLig(i - (M + 1), folha.getvLig(i));
        }
        cx2.setvLig(M, folha.getvLig(2 * M + 1));
        cx2.setTl(M);

        if (folha == pai) {
            folha.setvInfo(0, folha.getvInfo(M));
            folha.setvPos(0, folha.getvPos(M));
            folha.setTl(1);
            folha.setvLig(0, cx1);
            folha.setvLig(1, cx2);
        } else {
            pos = pai.procurarPosicao(folha.getvInfo(M));
            pai.remanejar(pos);
            pai.setvInfo(pos, folha.getvInfo(M));
            pai.setvLig(pos, cx1);
            pai.setvLig(pos + 1, cx2);
            pai.setTl(pai.getTl() + 1);
            if (pai.getTl() > 2 * M) {
                folha = pai;
                pai = localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    public void inserir(int info, int posArq) {
        No folha, pai;
        if (raiz == null) {
            raiz = new No(info, posArq);
        } else {
            folha = navegarAteFolha(info);
            int pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setvPos(pos, info);
            folha.setTl(folha.getTl() + 1);
            if (folha.getTl() > M * 2) {
                pai = localizarPai(folha, info);
                split(folha, pai);
            }
        }
    }

    public void in_ordem(No raiz) {
        if (raiz != null) {
            for (int i = 0; i < raiz.getTl(); i++) {
                in_ordem(raiz.getvLig(i));
                System.out.println(raiz.getvInfo(i));
            }
            in_ordem(raiz.getvLig(raiz.getTl()));
        }
    }

    public void in_ordem() {
        in_ordem(raiz);
    }

    // Métodos para a exclusão
    public No localizarNo(int info) {
        No atual = raiz;
        int pos;
        boolean achou = false;
        while (atual != null && !achou) {
            pos = atual.procurarPosicao(info);
            if (pos < atual.getTl() && info == atual.getvInfo(pos)) {
                achou = true;
            } else {
                atual = atual.getvLig(pos);
            }
        }
        return atual;
    }

    public No localizarSubE(No no, int pos) {
        no = no.getvLig(pos);
        while (no.getvLig(0) != null) {
            no = no.getvLig(no.getTl());
        }
        return no;
    }

    public No localizarSubD(No no, int pos) {
        no = no.getvLig(pos);
        while (no.getvLig(0) != null) {
            no = no.getvLig(0);
        }
        return no;
    }

    public void excluir(int info) {
        No no = localizarNo(info);
        No subE, subD, folha;
        int pos;
        if (no != null) {
            pos = no.procurarPosicao(info);
            if (no.getvLig(0) != null) { // não é folha
                subE = localizarSubE(no, pos);
                subD = localizarSubD(no, pos + 1);
                if (subE.getTl() > M || subD.getTl() == M) { // pegar sub da esq
                    no.setvInfo(pos, subE.getvInfo(subE.getTl() - 1));
                    no.setvPos(pos, subE.getvPos(subE.getTl() - 1));
                    folha = subE;
                    pos = subE.getTl() - 1;
                } else { // pegar sub da dir
                    no.setvInfo(pos, subD.getvInfo(0));
                    no.setvPos(pos, subD.getvPos(0));
                    folha = subD;
                    pos = 0;
                }
            } else {
                folha = no;
            }
            // Excluir da folha
            folha.remanejarEx(pos);
            folha.setTl(folha.getTl() - 1);
            if (folha == raiz && folha.getTl() == 0)
                raiz = null;
            else if (folha != raiz && folha.getTl() < M) {
                redistribuicao_concatena(folha);
            }
        }
    }

    public void redistribuicao_concatena(No folha) {
        No pai = localizarPai(folha, folha.getvInfo(0));
        int posP = pai.procurarPosicao(folha.getvInfo(0));
        No irmaE = null, irmaD = null;
        if (posP > 0)
            irmaE = pai.getvLig(posP - 1);
        if (posP < pai.getTl())
            irmaD = pai.getvLig(posP + 1);

        // Tentar redistribuição
        if (irmaE != null && irmaE.getTl() > M) {
            folha.remanejar(0);
            folha.setvInfo(0, pai.getvInfo(posP - 1));
            folha.setvPos(0, pai.getvPos(posP - 1));
            folha.setTl(folha.getTl() + 1);
            pai.setvInfo(posP - 1, irmaE.getvInfo(irmaE.getTl() - 1));
            pai.setvPos(posP - 1, irmaE.getvPos(irmaE.getTl() - 1));
            folha.setvLig(0, irmaE.getvLig(irmaE.getTl()));
            irmaE.setTl(irmaE.getTl() - 1);
        } else if (irmaD != null && irmaD.getTl() > M) {
            folha.setvInfo(folha.getTl(), pai.getvInfo(posP));
            folha.setvPos(folha.getTl(), pai.getvPos(posP));
            folha.setTl(folha.getTl() + 1);
            folha.setvLig(folha.getTl(), irmaD.getvLig(0));
            pai.setvInfo(posP, irmaD.getvInfo(0));
            pai.setvPos(posP, irmaD.getvPos(0));
            irmaD.remanejarEx(0);
            irmaD.setTl(irmaD.getTl() - 1);
        } else { // Não foi possível realizar a redistribuição
            // fazer concatenação
            if (irmaE != null) { //irmaE
                irmaE.setvInfo(irmaE.getTl(), pai.getvInfo(posP - 1));
                irmaE.setvPos(irmaE.getTl(), pai.getvPos(posP - 1));
                irmaE.setTl(irmaE.getTl() + 1);
                pai.remanejarEx(posP - 1);
                pai.setTl(pai.getTl() - 1);
                for (int i = 0; i < folha.getTl(); i++) {
                    irmaE.setvInfo(irmaE.getTl(), folha.getvInfo(i));
                    irmaE.setvPos(irmaE.getTl(), folha.getvPos(i));
                    irmaE.setvLig(irmaE.getTl(), folha.getvLig(i));
                    irmaE.setTl(irmaE.getTl() + 1);
                }
                irmaE.setvLig(irmaE.getTl(), folha.getvLig(folha.getTl()));
                pai.setvLig(posP - 1, irmaE);
            } else { // irmaD
                irmaD.remanejar(0);
                irmaD.setvInfo(0, pai.getvInfo(posP));
                irmaD.setvPos(0, pai.getvPos(posP));
                irmaD.setTl(irmaD.getTl() + 1);
                pai.remanejarEx(posP);
                pai.setTl(pai.getTl() - 1);
                for (int i = folha.getTl(); i > 0; i--) {
                    irmaD.remanejarEx(0);
                    irmaD.setvInfo(0, folha.getvInfo(i));
                    irmaD.setvPos(0, folha.getvPos(i));
                    irmaD.setvLig(0, folha.getvLig(i));
                    irmaD.setTl(irmaD.getTl() + 1);
                }
                pai.setvLig(posP, irmaD);
            }

            if (raiz.getTl() == 0) { // Pai == raiz
                if (irmaE != null)
                    raiz = irmaE;
                else
                    raiz = irmaD;
            } else if (pai != raiz && pai.getTl() < M) {
                folha = pai;
                redistribuicao_concatena(folha);
            }
        }
    }
}
