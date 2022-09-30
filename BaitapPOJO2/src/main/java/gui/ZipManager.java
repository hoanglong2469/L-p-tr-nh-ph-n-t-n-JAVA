package gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.bson.types.ObjectId;

import bus.ZipService;
import bus.impl.ZipServiceImpl;
import entity.Zip;


public class ZipManager extends JFrame{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ZipService zipService;
	private JButton btnFindById;

	public ZipManager() {
		
		setTitle("Zip manager");
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(800, 600));
		
		zipService = new ZipServiceImpl();
		
		Container content = getContentPane();
		Box b;
		content.add(b = Box.createHorizontalBox(), BorderLayout.NORTH);
		b.add(btnFindById = new JButton("Find by Id"));
		
		btnFindById.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = JOptionPane.showInputDialog("Enter the Id: ");
				if(id != null && !id.trim().equals("") && ObjectId.isValid(id)) {
					Zip zip = zipService.getZip(id);
					JOptionPane.showMessageDialog(null, zip);
				}else
					JOptionPane.showMessageDialog(null, "Id is not valid!");
			}
		});
		
	}
	
	public static void main(String[] args) {
		new ZipManager().setVisible(true);
	}

}
