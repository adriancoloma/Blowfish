import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
	private static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		CifradorBlowfish blowfish = new CifradorBlowfish(CifradorBlowfish.MODO_HEX);
		boolean continuar = true;
		
		while(continuar) {			
			System.out.println("¿Que desea realizar?\n 1. Cifrar 2. Descifrar");
			int decision = sc.nextInt();
			switch(decision) {
			case 1:
				System.out.print("Ingrese el texto plano: ");
				String textoPlanoEntrada = sc.next();
				System.out.print("Ingrese la clave como enteros separados por comas: ");
				int[] claveCifrado = convertirCadenaEnteros(sc.next());
				
				String textoCifrado = blowfish.cifrar(textoPlanoEntrada, claveCifrado);
				System.out.println("Texto cifrado: " + textoCifrado);
				break;
			
			case 2:
				System.out.print("Ingrese el texto cifrado: ");
				String textoCifradoEntrada = sc.next();
				System.out.print("Ingrese la clave como enteros separados por comas: ");
				int[] claveDescifrado = convertirCadenaEnteros(sc.next());
				
				String textoDescifrado = blowfish.descifrar(textoCifradoEntrada, claveDescifrado);
				System.out.println("Texto descifrado: " + textoDescifrado);
				break;
			
			default:
				continuar = false;
			}
			
		}
		
	}

	private static int[] convertirCadenaEnteros(String cadena) {
		StringTokenizer st = new StringTokenizer(cadena, ",");
		int[] cadenaEnteros = new int[st.countTokens()];
		
		for(int i = 0; i < cadenaEnteros.length; i++) {
			cadenaEnteros[i] = Integer.parseInt(st.nextToken());
		}
		
		return cadenaEnteros;
	}
}
