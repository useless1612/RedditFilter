package studi.RedditFilter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONException;

import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class GUI {

	private JFrame frame;
	private JTextField textField_remove;
	private JTextField textField_keep;
	private JLabel lbl_subreddit;
	private JTextField textField_Subreddit;
	private JScrollPane scrollPane;
	private JTable table;
	private DefaultTableModel tableModel;
	private JLabel lbl_rSlash;
	private JLabel lbl_sortby;
	private JButton btnGetPosts;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 800);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lbl_remove = new JLabel("remove");
		lbl_remove.setBounds(10, 39, 46, 14);
		frame.getContentPane().add(lbl_remove);
		
		textField_remove = new JTextField();
		textField_remove.setBounds(95, 36, 170, 20);
		frame.getContentPane().add(textField_remove);
		textField_remove.setColumns(10);
		
		JLabel lbl_keep = new JLabel("keep");
		lbl_keep.setBounds(10, 70, 46, 14);
		frame.getContentPane().add(lbl_keep);
		
		textField_keep = new JTextField();
		textField_keep.setBounds(95, 67, 170, 20);
		textField_keep.setColumns(10);
		frame.getContentPane().add(textField_keep);
		
		lbl_subreddit = new JLabel("Subreddit");
		lbl_subreddit.setBounds(10, 11, 66, 14);
		frame.getContentPane().add(lbl_subreddit);
		
		textField_Subreddit = new JTextField();
		textField_Subreddit.setBounds(95, 8, 170, 20);
		frame.getContentPane().add(textField_Subreddit);
		textField_Subreddit.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 110, 1116, 619);
		frame.getContentPane().add(scrollPane);
		
		tableModel = new DefaultTableModel(
			new String[][] {
			},
			new String[] {
				"Posts", "Link"
			});
		table = new JTable(tableModel);

		scrollPane.setViewportView(table);
		
		lbl_rSlash = new JLabel("r/");
		lbl_rSlash.setBounds(86, 11, 19, 14);
		frame.getContentPane().add(lbl_rSlash);
		
		lbl_sortby = new JLabel("sort by");
		lbl_sortby.setBounds(308, 11, 40, 14);
		frame.getContentPane().add(lbl_sortby);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(SortEnum.values()));
		comboBox.setBounds(358, 7, 95, 22);
		frame.getContentPane().add(comboBox);
		
		JButton btnApplyFilters = new JButton("apply Filters");
		btnApplyFilters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortEnum sortEnum = (SortEnum) comboBox.getSelectedItem();
				try {
					Posts filteredposts = App.useFilter(textField_Subreddit.getText(), sortEnum, textField_keep.getText(), textField_remove.getText());
					tableModel.setDataVector(filteredposts.getPostDataForTable(), new String[] { "Post", "Link"});
				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
				
			}
		});
		btnApplyFilters.setBounds(539, 49, 132, 35);
		frame.getContentPane().add(btnApplyFilters);
		
		btnGetPosts = new JButton("get Posts");
		btnGetPosts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortEnum sortEnum = (SortEnum) comboBox.getSelectedItem();
				try {
					Posts posts = App.getonlyPostsforGui(textField_Subreddit.getText(), sortEnum);
					tableModel.setDataVector(posts.getPostDataForTable(), new String[] { "Post", "Link"});
					comboBox.getSelectedItem();

				} catch (IOException | JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				
			}
		});
		btnGetPosts.setBounds(539, 7, 132, 31);
		frame.getContentPane().add(btnGetPosts);
	}
}
