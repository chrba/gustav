package de.chrb.gustav.model.gc;

import java.util.Optional;

import javax.annotation.Nonnull;


/**
 * Represents one minor or major garbage collection event of the JVM.
 *
 * @author Christian Bannes
 */
public interface GCEvent  {
	/**
	 * Indicates if the garbage collector uses stop-the-world, i.e.
	 * if all application threads are halted or if it runs in parallel
	 * with the application threads.
	 *
	 * @return true, if the GC uses stop-the-world, else false
	 */
	boolean isStopTheWorld();

	/**
	 * Indicates if this event is a minor garbage collection event
	 *
	 * @return true, if the GC event is a minor GC event, else false
	 */
	boolean isMinor();

	/**
	 * Memory statistics, e.g. how many garbage was freed.
	 *
	 * This is optional as not all gc events from a multi phase
	 * GC provide memory statistics, e.g. some CMS phases
	 *
	 * @return the memory statistics, optional
	 */
	@Nonnull Optional<GCMemStats> getMemStats();

	/**
	 * Timing statistics, e.g. how long did this gc event take, etc.
	 * @return the statistics
	 */
	@Nonnull GCTimeStats getTimeStats();

	/**
	 * The name specifiyng the type of this event, e.g. FULL GC or PSYoungGen
	 * @return the event name
	 */
	@Nonnull String getName();
}
