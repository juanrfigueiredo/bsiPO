import java.sql.SQLOutput;

public class AplicacaoBtree {

    public static void main(String[] args) {
        BTree arvore = new BTree();
        System.out.println("----------INSERCAO----------");
        for (int i = 1; i <= 10; i++) {
            arvore.inserir(i, i);
        }
        arvore.in_ordem();

        System.out.println("----------EXCLUSAO----------");
        arvore.excluir(6);
        arvore.in_ordem();
    }
}
