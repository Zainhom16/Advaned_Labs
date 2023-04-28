import java.io.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            String fN = args[0];
            if (!fN.endsWith(".arxml"))
                throw new NotValidAutosarException("Isn't an arxml file");

            File file = new File(fN);
            FileInputStream input = new FileInputStream(file);
            StringBuilder z = new StringBuilder();
            int y;
            boolean empty = true;
            while ((y = input.read()) != -1) {
                z.append((char) y);
                empty = false;
            }
            if (empty)
                throw new emptyAutosarFileException("Empty file");
            String code = z.toString();
            Scanner scanner = new Scanner(code);
            boolean before = true;
            StringBuilder aa = new StringBuilder();
            StringBuilder bb = new StringBuilder();
            ArrayList<Container> Containers = new ArrayList<Container>();
            while (scanner.hasNextLine()) {
                String ln = scanner.nextLine();
                if (ln.contains("<CONTAINER")) {
                    String id = ln.substring(ln.indexOf("UUID=\""), ln.indexOf("\">"));
                    ln = scanner.nextLine();
                    String sName = ln.substring(ln.indexOf("<SHORT-NAME>"), ln.indexOf("</SHORT-NAME>"));
                    ln = scanner.nextLine();
                    String lName = ln.substring(ln.indexOf("<LONG-NAME>"), ln.indexOf("</LONG-NAME>"));
                    Container c = new Container(id, sName, lName);
                    Containers.add(c);
                    before = false;
                    ln = scanner.nextLine();
                } else {
                    if (before)
                        aa.append((ln + '\n'));
                    else
                        bb.append((ln + '\n'));
                }
            }
            String bef = aa.toString();
            String aft = bb.toString();
            Collections.sort(Containers);

            String outputFileName = fN.substring(0, fN.indexOf(".")) + "_mod.arxml";
            FileOutputStream outputStream = new FileOutputStream(outputFileName);
            outputStream.write(bef.getBytes());
            for (Container c : Containers)
                outputStream.write(c.toString().getBytes());
            outputStream.write(aft.getBytes());
        } catch (NotValidAutosarException c1) {
            System.out.println(c1);
        } catch (emptyAutosarFileException c4) {
            System.out.println(c4);
        } catch (FileNotFoundException c2) {
            System.out.println(c2);
        } catch (IOException c3) {
            System.out.println(c3);
        }
    }
}

class Container implements Comparable<Container> {
    private String UUID;
    private String shortName;
    private String longName;
    public Container(String UUID, String short_name, String long_name) {
        this.UUID = UUID;
        shortName = short_name;
        longName = long_name;
    }

    @Override
    public int compareTo(Container o) {
        return shortName.compareTo(o.shortName);
    }

    public String toString() {
        return (
                "<CONTAINER " + UUID + "\">\n"
                        + shortName + "</SHORT-NAME>\n" +
                        longName + "</LONG-NAME>\n" +
                        "</CONTAINER>\n"
        );
    }
}
class NotValidAutosarException extends Exception{
    public NotValidAutosarException(String val) {
    }
}
class emptyAutosarFileException extends Exception {
    public emptyAutosarFileException(String val) {

    }
}