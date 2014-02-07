package org.androidanalyzer.core;

import org.androidanalyzer.core.IAnalyzerPlugin;

//	Interface to be used by each Analyzer Plugin to register
//  itself into the Core registry

interface IPluginRegistry {
    
    void registerPlugin(in String plugin);
    
}