package JsonWebToken;

public class InitToken {

  public static void main(String args[]) {

    CreateToken ct = new CreateToken();
    String token = ct.crearToken();
    System.out.println("Token creado:\n" + token);

    boolean verificar = ct.verificarToken(token);
    if (verificar) {
      System.out.println("Usuario autenticado");
    } else {
      System.out.println("Acceso denegado");
    }

  }

}
