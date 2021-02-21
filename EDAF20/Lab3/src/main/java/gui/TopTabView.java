package gui;

import javafx.scene.Parent;
import javafx.scene.control.Tab;
import datamodel.Database;
import javafx.fxml.FXML;

public class TopTabView {
	@FXML private Parent aLoginTab;
	@FXML private LoginTab aLoginTabController;

	@FXML private Parent aBookingTab;
	@FXML private BookingTab aBookingTabController;
	
	@FXML private Tab reservationTab;
	@FXML private ReservationsTab aReservationTabController;
	
	
	public void initialize() {
		System.out.println("Initializing TopTabView");
		
		// send the booking controller reference to the login controller
		// in order to pass data between the two
		aLoginTabController.setBookingTab(aBookingTabController);
		
		// When selection is changed, update the list
		reservationTab.setOnSelectionChanged(e -> {
			if(reservationTab.isSelected()) {
				// Initiate an update
				aReservationTabController.updateList();
			}
		});
	}
	
	public void setDatabase(Database db) {
		aLoginTabController.setDatabase(db);
		aBookingTabController.setDatabase(db);
		aReservationTabController.setDatabase(db);
	}
}
