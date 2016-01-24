import java.awt.*; 
import java.awt.event.*; 
import javax.swing.*; 
import javax.swing.event.*; 
import java.util.*; 
import java.io.*; 
import javax.sound.sampled.*; 
import javax.sound.sampled.AudioFormat.Encoding; 

class LCGs {
	private static int x;
	public static void setSeed(int seed) {
		x = seed % 4;
	}
	public static int Random() {
		x = (37 * x + 31) % (int)(1e9 + 7);
		return x % 4;
	}
}

class Main { 
	public static void main(String argv[]) {
		try {
			File file = new File(argv[0]);
			String path = file.getParent();
			String name = file.getName();
			int seed = 0;
			for (char ch : name.toCharArray()) {
				seed += ch;
			}
			LCGs.setSeed(seed);
			
			ArrayList<Integer> left_frames = new ArrayList<Integer>();
			ArrayList<Integer> down_frames = new ArrayList<Integer>();
			ArrayList<Integer> up_frames = new ArrayList<Integer>();
			ArrayList<Integer> right_frames = new ArrayList<Integer>();
			
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
					if (num > 15) break;
				}
				for (int i = 1; i < 299; i++) {
					if (data[(i - 1) * frame] < data[i * frame] && data[i * frame] >= data[(i+1) * frame]) {
						if (data[i * frame] > average * rate) {
							switch (LCGs.Random()) {
							case 0:
								left_frames.add(300 * count + i); break;
							case 1:
								down_frames.add(300 * count + i); break;
							case 2:
								up_frames.add(300 * count + i); break;
							case 3:
								right_frames.add(300 * count + i); break;
							}
							i += 15;
						}
					}
				}
				count ++;
			}
			for (int i = 0; i < left_frames.size(); i++) {
				pw.print(left_frames.get(i) + ",");
			}
			pw.println("");
			for (int i = 0; i < down_frames.size(); i++) {
				pw.print(down_frames.get(i) + ",");
			}
			pw.println("");
			for (int i = 0; i < up_frames.size(); i++) {
				pw.print(up_frames.get(i) + ",");
			}
			pw.println("");
			for (int i = 0; i < right_frames.size(); i++) {
				pw.print(right_frames.get(i) + ",");
			}
			pw.println("");
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
