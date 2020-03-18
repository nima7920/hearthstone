import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class logWriter {
    private File logFile;
    private FileWriter logFileWriter;

    public logWriter(Player currentPlayer) throws IOException {
        logFile = new File("logs//" + currentPlayer.getName() + "-" + currentPlayer.getId() + ".txt");

        if (logFile.exists()) {
            logFileWriter = new FileWriter(logFile, true);
            logFileWriter.write("\n"+ "Login At:" + getCurrentDate() + "\n");
            logFileWriter.flush();
        } else {
            logFileWriter = new FileWriter(logFile);
            logFileWriter.write("User Name:" + currentPlayer.getName() + "\n");
            logFileWriter.write("Password:" + currentPlayer.getPassword() + "\n");
            logFileWriter.write("CREATED AT:" + getCurrentDate() + "\n \n");
            logFileWriter.flush();
            logFileWriter.close();
            logFileWriter = new FileWriter(logFile, true); // reinitializing file writer
        }
    }

    // main function of the class:
    public void writeLog(String event, String description) throws IOException {
        logFileWriter.write(event + ":" + description + " AT:" + getCurrentDate() + "\n");
        logFileWriter.flush();
    }

    public void exiting(int exitType) throws IOException {
        if (exitType == 0) { // program is closing...
            logFileWriter.write("EXIT-GAME:" + getCurrentDate() + "\n");
            logFileWriter.flush();
        } else { // player is logging out ...
            logFileWriter.write("Logout at:" + getCurrentDate() + "\n");
            logFileWriter.flush();
        }
        logFileWriter.close();
    }

    public void deletePlayer() throws IOException {
        Scanner logFileReader=new Scanner(logFile);
        logFileWriter.close();
        String totalLog="";
        int i=0;
        while (logFileReader.hasNext()){
            i++;
            totalLog=totalLog+logFileReader.nextLine()+"\n";
            if(i==3){
                totalLog=totalLog+"DELETED-AT:"+getCurrentDate()+"\n";
            }
        }
        logFileWriter=new FileWriter(logFile);
        logFileWriter.write(totalLog);
        logFileWriter.flush();
        logFileWriter.close();
        logFileReader.close();
    }

    private String getCurrentDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return (dtf.format(now)).toString();
    }
}
