# QRESProject

**Formal specification foder** contains all the Proverif scripts used to prove the properties defined in the paper.

**Implementation** contains the source code of QRES and a document detailing the steps/sources needed to evaluate the QRES system. 

Furthermore, the implementation folder contains screen shots of (a) the encryption and transfer of tokens by the CSP (encrypted tokens are then saved in mongoDB), (b) encryption of each keyword (using garbled circuit) and then searching for each keyword over the encrypted tokens by the broker, and finally (c) saved tokens on the dynamoDB. We also show the Tor established circuit used.

**Proof** contains the semantics used to model our protocol. Data integrity is preserved if the final result obtained by both parties CSP Broker after a secured computation is consistent. This means that for the same inputs and the same function, both CSP and Broker find the same result. We prove this property using the correspondence property. We also prove the system secrecy by proving that the attacker learn nothing about the SLA.
