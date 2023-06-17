/**
*
* @author EMRE CAN SEÇER emre.secer@sakarya.edu.tr
* @since 06.04.2023
* <p>
* Motor.java dosyamı konsol parametresi ile alıp çalıştırıp yorum satırlarını tek tek almamı sağlayan ana programım
* </p>
*/

import java.io.*;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            return;
        }
        String file_name = args[0];
        try {

            BufferedReader reader = new BufferedReader(new FileReader(file_name));
            BufferedWriter javadocWriter = new BufferedWriter(new FileWriter("javadoc.txt"));
            BufferedWriter singleLineCommentWriter = new BufferedWriter(new FileWriter("teksatir.txt"));
            BufferedWriter multiLineCommentWriter = new BufferedWriter(new FileWriter("coksatir.txt"));
            String line;
            String function = "";
            String java_doc_comment = "";
            int javadocCount = 0;
            int singleLineCommentCount = 0;
            int multiLineCommentCount = 0;
            boolean javadoc_flag = false;
            boolean multiline_flag = false;
            boolean tmp_multi = false;


            String className = file_name.substring(0, file_name.indexOf("."));
            System.out.println("Sınıf: " + className);

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.trim().startsWith("/**")) {
                    javadocCount++;
                    java_doc_comment += line.trim() + "\n";
                    javadoc_flag = true;
                } else if (line.trim().startsWith("*/") && javadocCount > 0 && javadoc_flag) {
                    java_doc_comment += line.trim() + "\n\n";
                    javadoc_flag = false;
                } else if (line.trim().startsWith("*") && javadocCount > 0 && javadoc_flag) {
                    java_doc_comment += line.trim() + "\n\n";
                }


                if (line.trim().startsWith("public") && line.contains("(")) {


                    Pattern pattern = Pattern.compile("\\w+\\(");
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        if (!function.equals("")) {
                            System.out.println("\tFonksiyon: " + function);
                            System.out.println("\t\tTek Satır Yorum Sayısı: " + singleLineCommentCount);
                            System.out.println("\t\tCok Satırlı Yorum Sayısı: " + multiLineCommentCount);
                            System.out.println("\t\tJavadoc Yorum Sayısı: " + javadocCount);
                            System.out.println("----------------------------------------------");

                            javadocCount = 0;
                            singleLineCommentCount = 0;
                            multiLineCommentCount = 0;
                            javadocWriter.flush();
                            singleLineCommentWriter.flush();
                            multiLineCommentWriter.flush();


                        }

                        function = matcher.group().replace("(", "");

                        if (java_doc_comment != null && !java_doc_comment.isEmpty()) {

                            javadocWriter.write("Fonksiyon : " + function + "\n\n");
                            javadocWriter.write(java_doc_comment);
                            java_doc_comment = "";
                        }


                    }
                } else if (!function.equals("")) {

                    if (line.contains("//")) {
                        String regular_single_line = line.substring(line.indexOf("//"));
                        singleLineCommentCount++;
                        singleLineCommentWriter.write("Fonksiyon: " + function + "\n\n" + regular_single_line + "\n\n");
                    } else if (line.trim().startsWith("/*") && !line.contains("/**")) {
                        multiLineCommentWriter.write("Fonksiyon: " + function + "\n\n" + line.substring(line.indexOf("/*"), line.indexOf("*/") + 2) + "\n\n");
                        multiLineCommentCount++;
                        multiline_flag = true;
                    } else if (line.contains("*/") && multiline_flag) {
                        multiLineCommentWriter.write(line + "\n");
                        multiline_flag = false;
                    } else if (line.contains("*") && multiline_flag) {
                        multiLineCommentWriter.write(line + "\n");
                    }


                    if (line.contains("}")) {


                        System.out.println("\tFonksiyon: " + function);
                        System.out.println("\t\tTek Satır Yorum Sayısı: " + singleLineCommentCount);
                        System.out.println("\t\tCok Satırlı Yorum Sayısı: " + multiLineCommentCount);
                        System.out.println("\t\tJavadoc Yorum Sayısı: " + javadocCount);
                        System.out.println("----------------------------------------------");

                        if (java_doc_comment != null && !java_doc_comment.isEmpty() && function != null) {
                            javadocWriter.write("Fonksiyon : " + function + "\n\n");
                            javadocWriter.write(java_doc_comment);
                            java_doc_comment = "";

                        }
                        function = "";
                        javadocCount = 0;
                        singleLineCommentCount = 0;
                        multiLineCommentCount = 0;
                        javadocWriter.flush();
                        singleLineCommentWriter.flush();
                        multiLineCommentWriter.flush();

                    }

                }
            }
            reader.close();
            javadocWriter.close();
            singleLineCommentWriter.close();
            multiLineCommentWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

