package net.corda.demos.crowdFunding.structures

import net.corda.core.contracts.*
import net.corda.core.flows.FlowLogicRefFactory
import net.corda.core.identity.AbstractParty
import net.corda.demos.crowdFunding.flows.EndCampaign
import java.time.Instant
import java.util.*

data class Campaign(
        val name: String,
        val manager: AbstractParty,
        val target: Amount<Currency>,
        val raisedSoFar: Amount<Currency> = Amount(0, target.token),
        private val deadline: Instant,
        override val participants: List<AbstractParty> = listOf(manager),
        override val linearId: UniqueIdentifier = UniqueIdentifier()
) : LinearState, SchedulableState {
    override fun nextScheduledActivity(
            thisStateRef: StateRef,
            flowLogicRefFactory: FlowLogicRefFactory
    ): ScheduledActivity? {
        return ScheduledActivity(flowLogicRefFactory.create(EndCampaign::class.java, thisStateRef), deadline)
    }
}