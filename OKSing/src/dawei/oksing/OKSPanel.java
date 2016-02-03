package dawei.oksing;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingWorker;
import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class OKSPanel extends JPanel{

	private static final long serialVersionUID = -620586205401566309L;
	private Person person;
	private List<Song> songList;
	private JFrame window;
	private JCheckBox cb[];
	
	/**
	 * Create the panel.
	 */
	public OKSPanel(JFrame window) {
		
		this.setLayout(null);
		this.setSize(600, 550);
		this.setLocation(0, 0);
		this.window = window;
		String bk = "http://ok.okchang.com/home-10800746.html";
	//	String zhuo = "http://ok.okchang.com/home-10511316.html";
	//	String zishu = "http://ok.okchang.com/home-10362725.html";
		person = new Person(bk);	
		System.out.println(person.toString());
		
		initializeGUI();
	}
	
	public void initializeGUI() {
		
		// Initialize menu
		JMenu mFile; 
		JMenu mHelp; 
		JMenuItem miQuit; 
		JMenuItem miAbout; 
		
		JMenuBar mBar = new JMenuBar(); 
		mBar.setOpaque(true);

		mFile = new JMenu("File"); 
		mFile.setMnemonic(KeyEvent.VK_F); 
		miQuit = new JMenuItem("Quit"); 
		miQuit.setMnemonic(KeyEvent.VK_Q); 
		miQuit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);				
			}
			
		});
		mFile.add(miQuit); 

		mHelp = new JMenu("Help"); 
		mHelp.setMnemonic(KeyEvent.VK_H); 
		miAbout = new JMenuItem("About");	
		miAbout.setMnemonic(KeyEvent.VK_A);
		miAbout.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, 
						"BK Sing v1.0\n"+ "Date: 02/01/2016\n"+ "Author: Dawei Fan",
						"About",
						JOptionPane.INFORMATION_MESSAGE);			
			}
			
		});
		mHelp.add(miAbout);

		mBar.add(mFile); 
		mBar.add(mHelp); 
		window.setJMenuBar(mBar); 
		
		URL url;
		BufferedImage c = null;
		try {
			url = new URL(person.photoAddress);
			c = ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		ImageIcon image = new ImageIcon(c.getScaledInstance(160, 160,  java.awt.Image.SCALE_SMOOTH));
		JLabel lPhoto = new JLabel(image);
		lPhoto.setSize(new Dimension(160, 160));
		lPhoto.setLocation(10, 10);
		lPhoto.setVisible(true);
		add(lPhoto);
		
		JLabel lName = new JLabel(person.name);
		lName.setFont(new Font("Serif", Font.PLAIN, 35));
		lName.setLocation(180, 10);
		lName.setSize(160, 40);
		add(lName);
		
		JLabel lFollow = new JLabel("Followers " + person.numOfFans +"    Following: "+ person.numOfFriends);
		lFollow.setFont(new Font("Serif", Font.PLAIN, 25));
		lFollow.setLocation(180, 60);
		lFollow.setSize(420, 28);
		add(lFollow);
		
		JLabel lSongs = new JLabel("Songs: "+ person.numOfSongs);
		lSongs.setFont(new Font("Serif", Font.PLAIN, 25));
		lSongs.setLocation(180, 95);
		lSongs.setSize(280, 28);
		add(lSongs);
		
		JButton bListSongs = new JButton("List songs");
		bListSongs.setFont(new Font("Serif", Font.PLAIN, 20));
		bListSongs.setLocation(180, 130);
		bListSongs.setSize(150, 28);
		bListSongs.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel display = new JPanel();
				display.setSize(200, Integer.parseInt(person.numOfSongs)*30);
				display.setLayout(new GridLayout(Integer.parseInt(person.numOfSongs), 1));
				display.setLocation(10, 180);
				songList = person.listSongs();
				cb = new JCheckBox[Integer.parseInt(person.numOfSongs)];
				/*
				JPanel label = new JPanel(new GridLayout(1, 3));
				label.setSize(500, 30);
				label.setVisible(true);

				label.add(new JLabel("   Name"));
				label.add(new JLabel("Times"));
				label.add(new JLabel("Upload time"));
				display.add(label);
				*/
				for (int i = 0; i< songList.size(); i++) {
					Song song = songList.get(i);
					System.out.println(song.toString());
					
					JPanel row = new JPanel(new GridLayout(1, 3));
					row.setSize(330, 30);
					row.setLocation(0, 0);
					row.setVisible(true);
					
					cb[i] = new JCheckBox(song.title+"   ");
					row.add(cb[i]);
					row.add(new JLabel(song.numOfPlayed));
					row.add(new JLabel(song.uploadTime));
					display.add(row);
				}	
				
				JScrollPane spSongsList = new JScrollPane(display);
				spSongsList.setSize(565, 300);
				spSongsList.setLocation(10, 180);
				spSongsList.setVisible(true);
				spSongsList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				OKSPanel.this.add(spSongsList);
				spSongsList.validate();
				display.validate();
				//OKSPanel.this.validate();
				
			}
			
		});
		add(bListSongs);
		
		JButton bDownload = new JButton("Download");
		bDownload.setFont(new Font("Serif", Font.PLAIN, 20));
		bDownload.setLocation(350, 130);
		bDownload.setSize(150, 28);
		bDownload.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i< cb.length; i++) {
					cb[i].setEnabled(false);
				}
				
				for (int i = 0; i< cb.length; i++) {
					if (cb[i].isSelected()) {
						Song song = songList.get(i);
						System.out.println(i);
						URL website = song.getMediaAddress();
						int fileSize = Crawler.getFileSize(website);
						System.out.println("File Size: "+ fileSize);
						try {
							ReadableByteChannel rbc = Channels.newChannel(website.openStream());
							FileOutputStream fos = new FileOutputStream(song.title+".m4a");
							int bufferSize = 60000;
						//	fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
							
							ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
							int bytesRead = rbc.read(buffer);
							int loop = 0;
							
							while(bytesRead != -1 ) {
								loop++;
								buffer.flip();
								//		System.out.println("After Flip: Position: "+buf.position()+ " limit: "+buf.limit());
								while(buffer.hasRemaining())
									fos.write(buffer.get());
								if (loop % 10 == 0) {
									System.out.println("Progress "+ bufferSize*loop);
									System.out.format("progress: %2.2f%%\n", (double)10240*100*loop/(double)fileSize<100? (double)10240*100*loop/(double)fileSize:100);
								}
								buffer.clear();
							    bytesRead = rbc.read(buffer);
							}
							System.out.println("Downloaded: "+ loop*bufferSize);
							fos.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				
				for (int i = 0; i< cb.length; i++) {
					cb[i].setEnabled(true);
				}
				
			}
			
		});
		add(bDownload);
	}

	private class Download extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			
			return null;
		}
		
		@Override
		protected void done() {
			
		}
		
	}
}
