import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import javax.swing.event.*; 
import java.util.*; 
import java.io.*; 
import javax.sound.sampled.*; 
import javax.sound.sampled.AudioFormat.Encoding; 

class Main { 
	public static void main(String argv[]) {
		try {
			File file = new File(argv[0]);
			String path = file.getParent();
			File write = new File(path + "/scoreMaker3.score");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(write)));
			AudioInputStream ais = AudioSystem.getAudioInputStream(file);
			AudioFormat af = ais.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, af);
			SourceDataLine sdl = (SourceDataLine)AudioSystem.getLine(info);
			int count = 0;
			while (true) {
				final int size =  5 * (int)af.getSampleRate() * af.getFrameSize();
				final int frame = (int)af.getSampleRate() * af.getFrameSize() / 60;
				byte[] data = new byte[size];
				int readedSize = ais.read(data);
				if (readedSize == -1) {
					System.out.println("Finished.");
					break;
				}
				sdl.write(data, 0, readedSize);

				double average = 0;
				int n = 0;
				for (int j = 0; j < size; j++) {
					data[j] = (byte)(data[j] * data[j]);
					if (data[j] > 0) {
						average += data[j];
						n++;
					}
				}
				average /= n;
				int num = 0;
				double rate = 10.0;
				for (int i = frame; i < size; i += frame) {
					for (int j = 0; j < frame - 1; j++) {
						data[i] += data[i - j];
					}
				}
				for (; rate >= 1;rate -= 0.1) {
					num = 0;
					for (int i = 1; i < 299; i++) {
						if (data[(i - 1) * frame] < data[i * frame] && data[i * frame] >= data[(i+1) * frame]) {
							if (data[i * frame] > average * rate) {
								num++;
								i += 15;
							}
						}
					}
					if (num > 5) break;
				}
				for (int i = 1; i < 299; i++) {
					if (data[(i - 1) * frame] < data[i * frame] && data[i * frame] >= data[(i+1) * frame]) {
						if (data[i * frame] > average * rate) {
							pw.print(300 * count + i +",");
							i += 15;
						}
					}
				}
				count ++;
			}
			pw.println("\n50\n50\n50");
			pw.close();
		} catch (IOException e) {
			System.err.println("An IO error occured!!");
		} catch (UnsupportedAudioFileException e) {
			System.err.println("An UnsupportedAudioFile error occured!!");
		} catch (LineUnavailableException e) {
			System.err.println("A LineUnavailable error occured!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
} 
