import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaFinanceira {

    private String nome;
    private static final String ARQUIVO = "categorias.txt";
    private static List<CategoriaFinanceira> categorias = new ArrayList<>();



    public CategoriaFinanceira(String nome) {
      this.nome = nome;
    }

    public String getNome() {
         return nome;
    }

    public void setNome(String nome) {
     this.nome = nome;
    }


    public static List<CategoriaFinanceira> listar() {
      return new ArrayList<>(categorias);
    }




    public static void adicionar(String nome) {
     categorias.add(new CategoriaFinanceira(nome));
       salvarArquivo();
    }


    public static void editar(int index, String novoNome) {


        if (index >= 0 && index < categorias.size()) {
        categorias.get(index).setNome(novoNome);
     salvarArquivo();
        }
    }

    public static void excluir(int index) {
        if (index >= 0 && index < categorias.size()) {
            categorias.remove(index);
            salvarArquivo();
        }
    }





    public static void carregarArquivo() {
      categorias.clear();
    File arquivo = new File(ARQUIVO);

  if (!arquivo.exists()) return;

   try {
         BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
         String linha;

  while ((linha = leitor.readLine()) != null) {
   if (!linha.trim().isEmpty()) {
    categorias.add(new CategoriaFinanceira(linha.trim()));
            }
            }


    leitor.close();
   } catch (IOException e) {
     e.printStackTrace();
   }
    }




    public static void salvarArquivo() {
        try {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(ARQUIVO));

            for (CategoriaFinanceira cat : categorias) {
             escritor.write(cat.getNome());
             escritor.newLine();
            }





            escritor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
       }
