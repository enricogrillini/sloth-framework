package it.eg.sloth.webdesktop.controller.common.tab;

public interface BaseTabSelection {

  public void execSelectTab(String tabSheetName, String tabName) throws Exception;
  
  public void onSelectTab(String tabSheetName, String tabName) throws Exception;
  
}
