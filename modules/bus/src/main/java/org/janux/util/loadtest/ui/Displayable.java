package org.janux.util.loadtest.ui;

import java.util.Map;

public interface Displayable
{
	String getThreadName();
	void setThreadName(final String aName);
	String getCurrentTaskDescription();
	int getNumTasksCompleted();
	int getAverageTaskTime();
	Map<String, String> getStatistics();
}
