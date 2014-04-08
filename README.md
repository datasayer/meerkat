Meerkat
===============
Meerkat is a massive event log processing framework on top of Apache Hama, which provides high flexible features for aggregating, analyzing, and comparing data in real-time.

Meerkat consists of three major components:

 * GuardMeer - The "guard meerkats" are launched directly on the machines that generate the logs. They work as programmed. For example, when "guard meerkats" spot a specific event, they report to the all GuardMeer or BossMeer.
 * BossMeer - The boss of meerkats is usually aggregate the statistics.
 * MeerReporter - A real-time result storage. You can create your own storage by implementing MeerReportInterface, e.g., DBReporter, MemcachedReporter, ..., etc.

Future Tasks
===============

 * The current design requires the installation of Apache Hama on the every machines. This might be helpful for Fault Tolerance system in the future.
 * We might want to add something like a chain of bolts of Storm.

License
===============
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

(http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. See accompanying LICENSE file.

Related Sources
===============

 * http://codingwiththomas.blogspot.kr/2011/10/apache-hama-realtime-processing.html
 * https://issues.apache.org/jira/browse/HAMA-883