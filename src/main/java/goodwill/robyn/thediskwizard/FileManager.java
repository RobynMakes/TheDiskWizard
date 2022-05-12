package goodwill.robyn.thediskwizard;

import javafx.scene.control.TextArea;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>A class the contains methods that allow for direct file manipulation.</p>
 * <p>Several of the methods used in this class make use of the java.io.File package while others execute cmd commands
 * using java.lang.ProcessBuilder</p>
 * @see java.io.File
 * @see java.lang.ProcessBuilder
 */
public class FileManager {
    ArrayList<File> drives;
    String line;

    public FileManager() {
        drives = new ArrayList<>();
        this.rescan();
    }

    /**
     * A method that updates the drives ArrayList when called.
     */
    public void rescan(){
        drives.clear();
        drives.addAll(List.of(File.listRoots()));
    }

    /**
     * Returns the current list of drives in a File format.
     * @return an ArrayList containing all the drive File objects.
     */
    public ArrayList<File> getDrivesAsFile(){
        this.rescan();
        return drives;
    }

    /**
     * Takes in several parameters in order to construct and execute the cmd.exe format command
     * @param driveSelect   the drive that will be formatted.
     * @param fileSys       the file system the drive will be formatted to. ( this can be FAT, FAT32, exFAT, NTFS, UDF, or ReFS )
     * @param volume        the new volume label the drive will be assigned.
     * @param quickFormat   whether the drive will be formatted with Quick Format enabled. ( this is not recommended )
     * @see <a href="https://docs.microsoft.com/en-us/windows-server/administration/windows-commands/format">format</a>
     */
    public void format(String driveSelect, String fileSys, String volume, boolean quickFormat, TextArea output) throws IOException {

        // Creating the command that will be run from the command prompt.
        String formatCommand
                = "format " + driveSelect.substring(0,2) + " "
                + "/FS:" + fileSys;
        if (!volume.isEmpty()){
            formatCommand += " /V:" + volume;
        }
        if (quickFormat) {
            formatCommand += " /Q";
        }
        formatCommand += " /Y";

        // Setting up and executing cmd command.
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", formatCommand);
        processBuilder.redirectErrorStream(true);
        Process p = processBuilder.start();
        BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
        OutputStream os = p.getOutputStream();
        PrintWriter writer = new PrintWriter(os);
        writer.write('\n');
        while (true) {
            line = r.readLine();

            if (line == null) {
                break;
            }
            output.appendText("\n" + line);
            System.out.println(line);
        }
    }
}
