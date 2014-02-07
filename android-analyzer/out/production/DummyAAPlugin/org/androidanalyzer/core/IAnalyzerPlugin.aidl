package org.androidanalyzer.core;

import org.androidanalyzer.core.Data;

// 	Interface to be used with Analyzer Plugin

interface IAnalyzerPlugin {
	
	String getName();
	
	String getDescription();
    
    Data startAnalysis();
    
    void stopAnalysis();
    
    long getTimeout();
    
    void setDebugEnabled(boolean enabled);
	
	String getClassName();
	
	String getVersion();

	String getVendor();
	
	String getStatus();
	
	boolean isUIRequired();
}